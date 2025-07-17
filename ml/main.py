import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
import tensorflow as tf
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix
from sklearn.linear_model import LogisticRegression
import matplotlib.pyplot as plt
import seaborn as sns
import xgboost as xgb
import pymysql
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.neighbors import KNeighborsClassifier
import pickle

def save_tf_model_to_mysql(model, model_name, db_config):
    import tempfile
    import os

    # Save to a temporary directory
    with tempfile.TemporaryDirectory() as tmp_dir:
        model_path = os.path.join(tmp_dir, 'model.keras')
        model.save(model_path)

        # Read the model file as binary
        with open(model_path, 'rb') as f:
            model_blob = f.read()

        # Insert into MySQL
        connection = pymysql.connect(
            host='localhost',
            user='root',
            password='',
            database='test',  # <-- must be included
            port=3307
        )
        cursor = connection.cursor()

        cursor.execute("""
            CREATE TABLE IF NOT EXISTS models (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255),
                model LONGBLOB
            )
        """)

        cursor.execute("INSERT INTO models (name, model) VALUES (%s, %s)", (model_name, model_blob))

        connection.commit()
        cursor.close()
        connection.close()

def save_model_to_mysql(model, model_name, db_config):
    # Serialize model using pickle
    model_blob = pickle.dumps(model)

    # Connect to MySQL
    connection = pymysql.connect(
        host='localhost',
        user='root',
        password='',
        database='test',  # <-- must be included
        port=3307
    )
    cursor = connection.cursor()

    # Create table if not exists
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS models (
            id INT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255),
            model LONGBLOB
        )
    """)

    # Insert model
    cursor.execute("INSERT INTO models (name, model) VALUES (%s, %s)", (model_name, model_blob))

    connection.commit()
    cursor.close()
    connection.close()


db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'test',
    'port': '3307'
}

# Load the CSV file
data = pd.read_csv('output.csv')
data = data.drop(['matchId'], axis=1)
data = data.drop(['gameMode'], axis=1)
data = data.drop(['team1Id'], axis=1)
data = data.drop(['team2Id'], axis=1)


# Preview the data
print(data.head())

# 3. Define Features and Targets
X = data.drop(['t1Win', 't2Win'], axis=1)
y = data[['t1Win', 't2Win']]
print("✅ X shape (should be 38 features):", X.shape)  # (27000, 38)
print("✅ Columns in X:", X.columns.tolist())
# 4. Slice Data to Desired Size
total_samples = 27000  # 25000 train + 2000 test
X = X[:total_samples]
y = y[:total_samples]

# 5. Split Manually to Exact Sizes
X_train = X[:25000]
y_train = y[:25000]
X_test = X[25000:27000]
y_test = y[25000:27000]

# 6. Scale Features
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# 7. Build Model
model = tf.keras.Sequential([
    tf.keras.layers.Dense(64, activation='relu', input_shape=(X_train.shape[1],)),
    #tf.keras.layers.Dense(32, activation='relu'),
    tf.keras.layers.Dense(2, activation='sigmoid')  # 2 outputs: t1Win, t2Win
])

model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# 8. Train
history = model.fit(X_train, y_train, epochs=50, batch_size=256, validation_data=(X_test, y_test))

# 9. Evaluate
loss, accuracy = model.evaluate(X_test, y_test)
print(f'\nTest Loss: {loss:.4f}, Test Accuracy: {accuracy:.4f}')

# 10. Predict
predictions = model.predict(X_test)

predictions_int = (predictions > 0.5).astype(int)

print("\nPredictions (first 10):")
print(predictions_int[:10])

print("\nActual Values (first 10):")
print(y_test[:10])

# 11. Plot Training History
plt.plot(history.history['accuracy'], label='Train Accuracy')
plt.plot(history.history['val_accuracy'], label='Validation Accuracy DeepLearn')
plt.legend()
plt.title('Training & Validation Accuracy')
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.show()
print("Mean:", scaler.mean_)
print("Scale:", scaler.scale_)
print("Feature names:", X.columns.tolist())
save_tf_model_to_mysql(model, "TensorFlow_Model", db_config)


#weights, biases = model.layers[0].get_weights()

# Display the weights of the first layer
#print("Weights of the first layer (input layer):")
#print(weights)

# Display the biases of the first layer (if needed)
#print("\nBiases of the first layer:")
#print(biases)

# Create a DataFrame for weights with columns representing each feature (input)
#weights_df = pd.DataFrame(weights, columns=[f'Feature_{i}' for i in range(X_train.shape[1])])

# If the weights are for multiple neurons (64 in this case), let's handle that
#weights_df = pd.DataFrame(weights, columns=[f'Neuron_{i+1}' for i in range(weights.shape[1])])

# Print the DataFrame to see the weight values for each feature in each neuron
#print("\nWeights for each feature (column) and neuron:")
#print(weights_df)


# Calculate the correlation matrix
correlation_matrix = data.corr()

# Print the correlation matrix
print("Correlation Matrix:")
print(correlation_matrix)

# Create a heatmap to visualize the correlation matrix
plt.figure(figsize=(24, 24))  # Adjust the figure size for better readability
sns.heatmap(correlation_matrix, annot=True, cmap='coolwarm', fmt=".2f", linewidths=0.5)
plt.title("Correlation Matrix Heatmap")
plt.show()










# Load dataset
data = pd.read_csv('output.csv')
data = data.drop(['matchId'], axis=1)
data = data.drop(['gameMode'], axis=1)
data = data.drop(['team1Id'], axis=1)
data = data.drop(['team2Id'], axis=1)
# Drop non-numeric columns (if any) and fill missing values
data = data.select_dtypes(include=['number']).fillna(0)

# Define input features (X) and target labels (y)
X = data.drop(columns=['t1Win', 't2Win'])  # Features (all columns except target)
y = data[['t1Win', 't2Win']]  # Targets (t1Win and t2Win)

# Convert to single target column (assuming only one team can win per game)
y = y.idxmax(axis=1).apply(lambda x: 1 if x == 't1Win' else 0)

# Split dataset into training (25,000) and testing (2,000)
X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=25000, test_size=2000, random_state=42)

# Create and train the Gradient Boosted Tree model
model = xgb.XGBClassifier(n_estimators=100, learning_rate=0.1, max_depth=5, random_state=42)
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate the model
accuracy = accuracy_score(y_test, y_pred)
print(f"Model Accuracy: {accuracy:.4f}")
print("\nClassification Report:\n", classification_report(y_test, y_pred))

# Feature importance visualization
xgb.plot_importance(model, importance_type="weight", max_num_features=10)
plt.title("Feature Importance")
plt.show()
save_model_to_mysql(model, "GradientBoostedTree_Model", db_config)


# Get feature importance (gain)
feature_importance = model.get_booster().get_score(importance_type="gain")

# Convert to DataFrame
importance_df = pd.DataFrame(feature_importance.items(), columns=["Feature", "Importance"])

# Normalize importance values between 0 and 1
importance_df["Normalized Gini"] = importance_df["Importance"] / importance_df["Importance"].sum()

# Sort by importance
importance_df = importance_df.sort_values(by="Normalized Gini", ascending=False)

# Display the results
print(importance_df)


# Assuming 'X' and 'y' are defined and preprocessed (from your latest code block)

# Split the data (same way as you did for XGBoost)
X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=25000, test_size=2000, random_state=42)

# Create the Decision Tree classifier
dt_model = DecisionTreeClassifier(max_depth=5, random_state=42)

# Train the model
dt_model.fit(X_train, y_train)

# Make predictions
y_pred = dt_model.predict(X_test)

# Evaluate the model
accuracy = accuracy_score(y_test, y_pred)
print(f"Decision Tree Accuracy: {accuracy:.4f}")
print("\nClassification Report:\n", classification_report(y_test, y_pred))

# Visualize the decision tree (optional but useful)
plt.figure(figsize=(65, 15))
plot_tree(dt_model, feature_names=X.columns, class_names=["t2Win", "t1Win"], filled=True, fontsize=10)
plt.title("Decision Tree Visualization")
plt.show()
save_model_to_mysql(dt_model, "DecisionTree_Model", db_config)


# Split data (same as before)
X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=25000, test_size=2000, random_state=42)

# Initialize Logistic Regression model
log_model = LogisticRegression(max_iter=1000, solver='lbfgs')

# Train the model
log_model.fit(X_train, y_train)

# Predict on test set
y_pred = log_model.predict(X_test)

# Evaluate
accuracy = accuracy_score(y_test, y_pred)
print(f"Logistic Regression Accuracy: {accuracy:.4f}")
print("\nClassification Report:\n", classification_report(y_test, y_pred))

# Optional: Confusion matrix
conf_matrix = confusion_matrix(y_test, y_pred)
plt.figure(figsize=(6, 4))
sns.heatmap(conf_matrix, annot=True, fmt='d', cmap='Blues', xticklabels=['t2Win', 't1Win'], yticklabels=['t2Win', 't1Win'])
plt.xlabel('Predicted')
plt.ylabel('Actual')
plt.title('Confusion Matrix - Logistic Regression')
plt.show()
save_model_to_mysql(log_model, "LogisticRegression_Model", db_config)




scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, train_size=25000, test_size=2000, random_state=42)

# Create and train KNN model
knn_model = KNeighborsClassifier(n_neighbors=6)  # You can tune this value later
knn_model.fit(X_train, y_train)

# Predict
y_pred = knn_model.predict(X_test)

# Evaluate
accuracy = accuracy_score(y_test, y_pred)
print(f"KNN Accuracy: {accuracy:.4f}")
print("\nClassification Report:\n", classification_report(y_test, y_pred))

# Confusion matrix
conf_matrix = confusion_matrix(y_test, y_pred)
plt.figure(figsize=(6, 4))
sns.heatmap(conf_matrix, annot=True, fmt='d', cmap='Greens', xticklabels=['t2Win', 't1Win'], yticklabels=['t2Win', 't1Win'])
plt.xlabel('Predicted')
plt.ylabel('Actual')
plt.title('Confusion Matrix - KNN')
plt.show()
save_model_to_mysql(knn_model, "KNN_Model", db_config)