import React, { useState } from 'react';
import './LoginForm.css';
import { FaRegUserCircle, FaKey } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";




const LoginForm = () => {
    // State hooks to store email, password, and response message
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [responseMessage, setResponseMessage] = useState('');
    const navigate = useNavigate(); // React Router's navigation hook

    // Handle form submission
    const handleLogin = async (e) => {
        e.preventDefault(); // Prevent page reload
    
        try {
            // Make the POST request
            const response = await fetch('http://localhost:8080/api/v1/user/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json', // Ensure this is correct
                },
                body: JSON.stringify({
                    email,
                    password
                  }), // Pass the form data as body
            });

            const data = await response.json(); // Parse JSON response

            if (response.ok && data.status) {
                // Only redirect if the response indicates success
                localStorage.setItem("authToken", data.token); // Optional: Store the token
                navigate('/machinelearning'); // Redirect to the machinelearning page
            } else {
                // Display error message from backend
                setResponseMessage(data.message || "Login failed. Please try again.");
            }
        } catch (error) {
            console.error('Error:', error);
            setResponseMessage('Something went wrong. Please try again later.');
        }
    };
    

    return (
        <div className="wrapper">
            


            <form onSubmit={handleLogin}>
                <h1>Login</h1>
                
                {/* Email Input */}
                <div className="input-box">
                    <input
                        type="text"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)} // Update email on change
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
                        onChange={(e) => setPassword(e.target.value)} // Update password on change
                        required
                    />
                    <FaKey className="icon" />
                </div>
                
                {/* Remember Me Checkbox and Forgot Password Link */}
                <div className="remember-forgot">
                    <label>
                        <input type="checkbox" />
                        Remember me <a href="#">Forgot password?</a>
                    </label>
                </div>
                
                {/* Submit Button */}
                <div>
                    <button type="submit">Login</button>
                </div>

                {/* Response Message */}
                <div>
                    {responseMessage && <p>{responseMessage}</p>}
                </div>

                {/* Register Link */}
                <div className="register-link">
                    <p>Don’t have an account? <Link to="/register">Register</Link></p>
                </div>
            </form>
        </div>
    );
};

export default LoginForm;
