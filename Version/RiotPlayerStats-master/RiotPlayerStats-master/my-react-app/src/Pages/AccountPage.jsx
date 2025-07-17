import React, { useState } from 'react';

const AccountPage = () => {
    const [matchId, setMatchId] = useState("");
    const [puuid, setPuuid] = useState("");

    const handleLogout = () => {
        localStorage.removeItem("authToken"); // Remove token on logout
        window.location.href = "/login"; // Redirect to login
    };

    // Make handleSubmit async to use await inside it
    const handleSubmit = async (event) => {
        event.preventDefault(); // Prevent default form submission
        console.log("Match ID:", matchId);
        console.log("PUUID:", puuid);

        try {
            const response = await fetch(`http://localhost:8080/api/v1/matchdata/fetch?matchId=${matchId}&puuid=${puuid}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('authToken')}` // Send token if available
                },
            });
            
            
            if (response.ok) {
                const data = await response.json(); // Parse the JSON data if the response is successful
                console.log('Data fetched:', data);
            } else {
                console.error('Error fetching data:', response.status);
            }
        } catch (error) {
            console.error('Network error or something went wrong:', error);
        }
    };

    return (
        <div>
            <h1>Welcome to Your Account</h1>
            <button onClick={handleLogout}>Logout</button>

            <form onSubmit={handleSubmit} style={{ marginTop: "20px" }}>
                <div style={{ marginBottom: "10px" }}>
                    <label htmlFor="matchId">Match ID: </label>
                    <input
                        type="text"
                        id="matchId"
                        value={matchId}
                        onChange={(e) => setMatchId(e.target.value)}
                        placeholder="Enter Match ID"
                    />
                </div>
                <div style={{ marginBottom: "10px" }}>
                    <label htmlFor="puuid">PUUID: </label>
                    <input
                        type="text"
                        id="puuid"
                        value={puuid}
                        onChange={(e) => setPuuid(e.target.value)}
                        placeholder="Enter PUUIDENTERRR"
                    />
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default AccountPage;
