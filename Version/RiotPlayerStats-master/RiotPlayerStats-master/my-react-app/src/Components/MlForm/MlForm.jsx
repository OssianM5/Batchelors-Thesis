import React, { useState, useEffect } from 'react';
import './MlForm.css'; // Make sure this includes the grid styles
import { Link } from "react-router-dom";

const featureNames = [
    'Game ID',
    'Game Duration (seconds)',
    'Team 1 Feats (0,1,2,3 or 1001)',
    'Team 1 Feats Blood (0,1,2,3 or 1001)',
    'Team 1 First Turret (0,1 or 1001)',
    'Team 1 Atakhan First (0 or 1)',
    'Team 1 Atakhan Kills',
    'Team 1 Baron First (0 or 1)',
    'Team 1 Baron Kills',
    'Team 1 First Blood (0 or 1)',
    'Team 1 Champion Kills',
    'Team 1 Dragon First (0 or 1)',
    'Team 1 Dragon Kills',
    'Team 1 Void Grubs First (0 or 1)',
    'Team 1 Void Grubs Kills',
    'Team 1 Inhibitor First (0 or 1)',
    'Team 1 Inhibitor Kills',
    'Team 1 Rift Herald First (0 or 1)',
    'Team 1 Rift Herald Kills',
    'Team 1 Tower First (0 or 1)',
    'Team 1 Tower Kills',

    'Team 2 Feats (0,1,2,3 or 1001)',
    'Team 2 Feats Blood (0,1,2,3 or 1001)',
    'Team 2 First Turret (0,1 or 1001)',
    'Team 2 Atakhan First (0 or 1)',
    'Team 2 Atakhan Kills',
    'Team 2 Baron First (0 or 1)',
    'Team 2 Baron Kills',
    'Team 2 First Blood (0 or 1)',
    'Team 2 Champion Kills',
    'Team 2 Dragon First (0 or 1)',
    'Team 2 Dragon Kills',
    'Team 2 Void Grubs First (0 or 1)',
    'Team 2 Void Grubs Kills',
    'Team 2 Inhibitor First (0 or 1)',
    'Team 2 Inhibitor Kills',
    'Team 2 Rift Herald First (0 or 1)',
    'Team 2 Rift Herald Kills',
    'Team 2 Tower First (0 or 1)',
    'Team 2 Tower Kills'
];




const MlForm = () => {
    const [menuOpen, setMenuOpen] = useState(false);
    const [features, setFeatures] = useState(Array(40).fill(''));
    const [modelName, setModelName] = useState('');
    const [availableModels, setAvailableModels] = useState([]); // ⬅️ New state
    const [responseMessage, setResponseMessage] = useState('');

    useEffect(() => {
        fetch('http://localhost:8000/models/')
            .then(res => res.json())
            .then(data => setAvailableModels(data))
            .catch(err => console.error("Failed to load models", err));
    }, []);

    const [profileData, setProfileData] = useState(null);
    const [showProfile, setShowProfile] = useState(false);

    const handleProfileClick = async () => {
        try {
            const token = localStorage.getItem("authToken");

            const response = await fetch("http://localhost:8080/api/v1/user/account", {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token") 
                }
            });

            if (!response.ok) throw new Error("Failed to fetch user profile");

            const data = await response.json();
            setProfileData(data);
            setShowProfile(true);
            setMenuOpen(false);
        } catch (error) {
            console.error("Profile fetch failed:", error);
            alert("Unable to fetch profile. Are you logged in?");
        }
    };

    const handleLogout = () => {
        localStorage.removeItem("authToken"); // Remove token on logout
        window.location.href = "/login"; // Redirect to login
    };

    const handleFeatureChange = (index, value) => {
        const updated = [...features];
        updated[index] = value;
        setFeatures(updated);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const numericFeatures = features.map(val => Number(val));

        try {
            const response = await fetch('http://localhost:8000/predict/', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ features: numericFeatures, model_name: modelName }),
            });

            const data = await response.text();
            setResponseMessage(data);
        } catch (error) {
            console.error('Error:', error);
            setResponseMessage('Something went wrong. Please try again later.');
        }
    };

    return (
        
        
        <div className="wrapper">
            <form onSubmit={handleSubmit}>

                <div className="navbar">
                    <div className="menu-icon" onClick={() => setMenuOpen(!menuOpen)}>
                        &#9776;
                    </div>
                    {menuOpen && (
                        <div className="menu-dropdown">
                        <Link to="/home" onClick={() => setMenuOpen(false)}>Home</Link>
                        <Link to="/register" onClick={() => setMenuOpen(false)}>Register</Link>
                        <Link to="/login" onClick={() => setMenuOpen(false)}>Login</Link>
                        <Link to="/machinelearning" onClick={() => setMenuOpen(false)}>MachineLearning</Link>
                        <Link to="/history" onClick={() => setMenuOpen(false)}>History</Link>
                        <Link to="/balance" onClick={() => setMenuOpen(false)}>Balancing ARAM</Link>
                        </div>
                    )}
                </div>

                <h1>ML Prediction Form</h1>

                <div className="explanation-box">
                    <p>
                        Pentru a folosi aplicația de predicție a câștigului trebuie inserate datele meciului, selectat modelul de predicție dorit iar răspunsul va fi de forma [x,y] sau [x] în funcție de modelul selectat, pentru modele cu 2 răspunsuri prima cifra arată dacă echipa 1 a câștigat sau nu, iar a doua cifră arată răspunsul pentru echipa 2. Pentru răspunsul de forma [x] echipa 1 câștigă în cazul în care răspunsul este 0, respectiv echipa 2 pentru răspunsul 1. Pentru datele in care avem raspuns 0, 1, 2, 3 sau 1001, se introduce 0,1,2 si 3 pentru echipa castigatoare feats conform meciului, iar 1001 pentru echipa pierzatoare feats.
                    </p>
                </div>

                <div className="form-grid">
                    {featureNames.map((name, index) => (
                        <div className="input-box grid-item" key={index}>
                            <label htmlFor={`feature-${index}`}>{name}</label>
                            <input
                                id={`feature-${index}`}
                                type="number"
                                placeholder={`Enter ${name}`}
                                title={`Enter a number for ${name}`}
                                value={features[index]}
                                onChange={(e) => handleFeatureChange(index, e.target.value)}
                                required
                            />
                        </div>
                    ))}
                </div>

                {/* Model Selector Dropdown */}
                <div className="input-box">
                    <label htmlFor="model-name">Select Model</label>
                    <select
                        id="model-name"
                        value={modelName}
                        onChange={(e) => setModelName(e.target.value)}
                        required
                    >
                        <option value="">-- Select a Model --</option>
                        {availableModels.map((model, idx) => (
                            <option key={idx} value={model}>
                                {model}
                            </option>
                        ))}
                    </select>
                </div>

                {showProfile && profileData && (
                    <div className="profile-card">
                        <h3>User Profile</h3>
                        <p><strong>Game Name:</strong> {profileData.gameName}</p>
                        <p><strong>Tag Line:</strong> {profileData.tagLine}</p>
                        <p><strong>Email:</strong> {profileData.email}</p>
                    </div>
                )}
                

                <button type="submit">Predict</button>
                {responseMessage && <p>{responseMessage}</p>}

                <div className="login-link">
                    <p>Back to <Link to="/">Home</Link></p>
                </div>
            </form>
        </div>
    );
};

export default MlForm;