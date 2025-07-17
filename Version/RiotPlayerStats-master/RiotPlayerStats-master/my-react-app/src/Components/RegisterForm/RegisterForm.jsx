import React, { useState } from 'react';
import './RegisterForm.css';
import { FaRegUserCircle, FaKey, FaUser, FaTag } from "react-icons/fa";
import { Link } from "react-router-dom";


const RegisterForm = () => {
    // State hooks for form fields and response message
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [summonerName, setSummonerName] = useState('');
    const [tagline, setTagline] = useState('');
    const [responseMessage, setResponseMessage] = useState('');

    // Handle form submission
    const handleRegister = async (e) => {
        e.preventDefault(); // Prevent page reload

        try {
            // Make the POST request
            const response = await fetch('http://localhost:8080/api/v1/user/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email,
                    password,
                    summonerName,
                    tagline
                }),
            });

            const data = await response.text(); // Assuming the backend sends a plain text response
            setResponseMessage(data); // Update the response message
        } catch (error) {
            console.error('Error:', error);
            setResponseMessage('Something went wrong. Please try again later.');
        }
    };

    return (
        <div className="wrapper">
            <form onSubmit={handleRegister}>
                <h1>Register</h1>

                {/* Email Input */}
                <div className="input-box">
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <FaRegUserCircle className="icon" />
                </div>

                {/* Password Input */}
                <div className="input-box">
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <FaKey className="icon" />
                </div>

                {/* Summoner Name Input */}
                <div className="input-box">
                    <input
                        type="text"
                        placeholder="Summoner Name"
                        value={summonerName}
                        onChange={(e) => setSummonerName(e.target.value)}
                        required
                    />
                    <FaUser className="icon" />
                </div>

                {/* Tag Line Input */}
                <div className="input-box">
                    <input
                        type="text"
                        placeholder="Tag Line"
                        value={tagline}
                        onChange={(e) => setTagline(e.target.value)}
                        required
                    />
                    <FaTag className="icon" />
                </div>

                {/* Submit Button */}
                <div>
                    <button type="submit">Register</button>
                </div>

                {/* Response Message */}
                <div>
                    {responseMessage && <p>{responseMessage}</p>}
                </div>

                {/* Login Link */}
                <div className="login-link">
                    <p>Already have an account? <Link to="/login">Login</Link></p>
                </div>
            </form>
        </div>
    );
};

export default RegisterForm;
