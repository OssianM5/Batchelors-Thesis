import React from 'react';
import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
    const authToken = localStorage.getItem("authToken");

    // If no token exists, redirect to login page
    if (!authToken) {
        return <Navigate to="/login" />;
    }

    // If authenticated, render the requested page
    return children;
};

export default ProtectedRoute;
