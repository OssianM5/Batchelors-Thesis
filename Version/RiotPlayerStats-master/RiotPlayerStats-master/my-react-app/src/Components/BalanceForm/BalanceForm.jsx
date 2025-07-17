import React, { useState } from 'react';
import './BalanceForm.css'; // You'll create this CSS file for styling
import { Link } from 'react-router-dom';

const BalanceForm = () => {
  const [numbers, setNumbers] = useState(Array(10).fill(''));
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');
  const [menuOpen, setMenuOpen] = useState(false); // ✅ Add this

  const handleChange = (index, value) => {
    const updated = [...numbers];
    updated[index] = value;
    setNumbers(updated);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setResult(null);

    const intValues = numbers.map(n => parseInt(n));
    if (intValues.some(isNaN)) {
      setError('Please enter 10 valid integers.');
      return;
    }

    try {
      const response = await fetch('http://localhost:8000/balance/', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ numbers: intValues }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.detail || 'Request failed.');
      }

      const data = await response.json();
      setResult(data);
    } catch (err) {
      setError(err.message || 'Something went wrong.');
    }
  };

  return (
    <div className="wrapper">
          <div className="header">
              <div className="navbar">
                  <div className="menu-icon" onClick={() => setMenuOpen(!menuOpen)}>
                      &#9776;
                  </div>
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

      <h1>Team Balancer</h1>
          <div className="explanation-box">
              <p>
                  Introduceți punctele jucătorilor pentru echipa custom de All Random All Mid, iar răspunsul programului va grupa cele 10 valori pentru o balansare cât mai corectă a jucătorilor. Intrați în camera de joc și luați pozițiile precizate.
              </p>
          </div>

      <form onSubmit={handleSubmit}>
        <div className="balance-grid">
          {numbers.map((num, idx) => (
            <input
              key={idx}
              type="number"
              placeholder={`#${idx + 1}`}
              value={num}
              onChange={(e) => handleChange(idx, e.target.value)}
              required
            />
          ))}
        </div>

        <button type="submit">Balance</button>

        {error && <p className="error">{error}</p>}

        {result && (
          <div className="result-box">
            <h3>Balanced Teams</h3>
            <p><strong>Group 1:</strong> {result.group1.join(', ')} (Sum: {result.group1_sum})</p>
            <p><strong>Group 2:</strong> {result.group2.join(', ')} (Sum: {result.group2_sum})</p>
            <p><strong>Difference:</strong> {result.difference}</p>
          </div>
        )}
      </form>
    </div>
  );
};

export default BalanceForm;
