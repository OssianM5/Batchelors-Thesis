import tempfile

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import pymysql
import pickle
import numpy as np
import tensorflow as tf
import io
from sklearn.preprocessing import StandardScaler
from fastapi.middleware.cors import CORSMiddleware
from itertools import combinations

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Change to ["http://localhost:3000"] for stricter security
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# MySQL config
db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'test',
    'port': 3307,
    'cursorclass': pymysql.cursors.DictCursor
}

# Input schema
class InputData(BaseModel):
    features: list[float]
    model_name: str

class BalanceInput(BaseModel):
    numbers: list[int]

# Dummy pre-fitted scaler (should match your training one)
scaler = StandardScaler()
scaler.mean_ = np.array([1.25005000e+04, 1.77025472e+03, 2.48921680e+02, 4.88992560e+02,
 4.09097280e+02, 3.57040000e-01, 3.57040000e-01, 2.56800000e-01,
 3.31800000e-01, 4.90680000e-01, 3.18521200e+01, 4.65240000e-01,
 1.92572000e+00, 4.99000000e-01, 2.80604000e+00, 4.07760000e-01,
 9.78160000e-01, 4.31120000e-01, 4.31120000e-01, 4.82920000e-01,
 5.72148000e+00, 2.53553560e+02, 4.88672720e+02, 4.09817280e+02,
 3.79320000e-01, 3.79320000e-01, 2.87440000e-01, 3.76080000e-01,
 4.88040000e-01, 3.20354400e+01, 5.03840000e-01, 2.00860000e+00,
 4.64760000e-01, 2.62840000e+00, 4.00160000e-01, 9.65040000e-01,
 4.09360000e-01, 4.09360000e-01, 4.80240000e-01, 5.64980000e+00])  # Fill in with training mean
scaler.scale_ = np.array([7.21687836e+03, 5.30180936e+02, 4.31156752e+02, 4.98904030e+02,
 4.91667360e+02, 4.79126746e-01, 4.79126746e-01, 4.36868127e-01,
 5.64932527e-01, 4.99913130e-01, 1.46294119e+01, 4.98790279e-01,
 1.41312508e+00, 4.99999000e-01, 2.14655527e+00, 4.91418134e-01,
 1.32921143e+00, 4.95232820e-01, 4.95232820e-01, 4.99708188e-01,
 3.78639494e+00, 4.33840963e+02, 4.98896071e+02, 4.91801397e+02,
 4.85217825e-01, 4.85217825e-01, 4.52568499e-01, 5.98668384e-01,
 4.99856938e-01, 1.48219035e+01, 4.99985254e-01, 1.43687370e+00,
 4.98756596e-01, 2.13145805e+00, 4.89930581e-01, 1.31562069e+00,
 4.91715762e-01, 4.91715762e-01, 4.99609390e-01, 3.78950656e+00])  # Fill in with training std dev
scaler.n_features_in_ = 40  # or whatever the expected number is

# Load model from DB
def load_model_from_mysql(model_name):
    connection = pymysql.connect(**db_config)
    cursor = connection.cursor()
    cursor.execute("SELECT model FROM models WHERE name = %s ORDER BY id DESC LIMIT 1", (model_name,))
    result = cursor.fetchone()
    connection.close()

    if not result:
        raise ValueError("Model not found in the database.")

    model_blob = result['model']

    # First try loading with pickle (for sklearn, GradientBoostedTree, XGBoost, KNN, LogisticRegression, etc.)
    try:
        model = pickle.loads(model_blob)
        return model, "sklearn"
    except pickle.UnpicklingError:
        pass  # Only if this fails, attempt TensorFlow load

    # Otherwise assume it's TensorFlow model stored as a .keras file
    with tempfile.TemporaryDirectory() as tmp_dir:
        model_path = tmp_dir + "/model.keras"
        with open(model_path, 'wb') as f:
            f.write(model_blob)
        model = tf.keras.models.load_model(model_path)
        return model, "tensorflow"

@app.get("/models/")
def get_model_names():
    try:
        connection = pymysql.connect(**db_config)
        cursor = connection.cursor()
        cursor.execute("SELECT DISTINCT name FROM models")
        rows = cursor.fetchall()
        connection.close()
        return [row["name"] for row in rows]
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))



@app.get("/predictions/")
def get_predictions():
    try:
        connection = pymysql.connect(**db_config)
        cursor = connection.cursor()
        cursor.execute("SELECT * FROM predictions ORDER BY created_at DESC")
        rows = cursor.fetchall()
        connection.close()
        return rows
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/predict/")
def predict(data: InputData):
    try:
        model, model_type = load_model_from_mysql(data.model_name)
        features_array = np.array(data.features).reshape(1, -1)

        # Preprocess (scale)
        features_scaled = scaler.transform(features_array)

        # Generate prediction
        if model_type == "sklearn":
            prediction = model.predict(features_scaled)
        elif model_type == "tensorflow":
            prediction = model.predict(features_scaled)
            prediction = (prediction > 0.5).astype(int)

        prediction_result = prediction.tolist()

        # Store input + prediction in DB
        try:
            connection = pymysql.connect(**db_config)
            cursor = connection.cursor()
            insert_query = """
                INSERT INTO predictions (model_name, features, prediction)
                VALUES (%s, %s, %s)
            """
            cursor.execute(insert_query, (
                data.model_name,
                str(data.features),  # Store as string or use JSON if you want structured access
                str(prediction_result)
            ))
            connection.commit()
        finally:
            connection.close()

        return {"prediction": prediction_result}

    except ValueError as ve:
        raise HTTPException(status_code=404, detail=str(ve))
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/balance/")
def balance_teams(data: BalanceInput):
    if len(data.numbers) != 10:
        raise HTTPException(status_code=400, detail="You must provide exactly 10 integers.")

    nums = data.numbers
    total_sum = sum(nums)
    target = total_sum / 2
    best_diff = float('inf')
    best_combo = None

    # Generate all combinations of 5 numbers from the list of 10
    for combo in combinations(nums, 5):
        group1 = list(combo)
        group2 = nums.copy()
        for n in group1:
            group2.remove(n)
        diff = abs(sum(group1) - sum(group2))
        if diff < best_diff:
            best_diff = diff
            best_combo = (group1, group2)

    return {
        "group1": best_combo[0],
        "group2": best_combo[1],
        "group1_sum": sum(best_combo[0]),
        "group2_sum": sum(best_combo[1]),
        "difference": abs(sum(best_combo[0]) - sum(best_combo[1]))
    }
