import React, { useEffect, useState } from 'react';
import './HistoryForm.css';
import { Link } from 'react-router-dom';

const HistoryForm = () => {
    const [history, setHistory] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [menuOpen, setMenuOpen] = useState(false); // ✅ Added
    const [profileData, setProfileData] = useState(null); // Optional
    const [showProfile, setShowProfile] = useState(false); // Optional

    useEffect(() => {
        fetch('http://localhost:8000/predictions/')
            .then((res) => res.json())
            .then((data) => {
                setHistory(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                setError('Failed to load prediction history.');
                setLoading(false);
            });
    }, []);

    const handleProfileClick = async () => {
        try {
            const token = localStorage.getItem("authToken");

            const response = await fetch("http://localhost:8080/api/v1/user/account", {
                headers: {
                    Authorization: "Bearer " + token
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

    return (
        <div className="wrapper">
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

            <h1>History</h1>
            {loading && <p>Loading...</p>}
            {error && <p className="error">{error}</p>}
            {!loading && !error && (
                <div className="history-table-container">
                    <table className="history-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Model</th>
                                <th>Features</th>
                                <th>Prediction</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {history.map((record) => (
                                <tr key={record.id}>
                                    <td>{record.id}</td>
                                    <td>{record.model_name}</td>
                                    <td><pre>{record.features}</pre></td>
                                    <td>{record.prediction}</td>
                                    <td>{new Date(record.created_at).toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default HistoryForm;
