package com.example.demo.RiotApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class MatchHistoryFetcher {
    private static final String MATCH_API_REGION = "europe"; // Adjust based on region

    // Fetch a single match ID based on the player's PUUID and desired number of matches
    public String fetchMatchIds(String puuid, int count) {
        String endpoint = String.format("https://%s.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?count=%d",
                MATCH_API_REGION, puuid, count);

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Riot-Token", RiotApiConfig.getApiKey()); // Retrieve the API key from RiotApiConfig

            // Check for successful response
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response to extract match IDs
                JsonArray matchArray = JsonParser.parseString(response.toString()).getAsJsonArray();

                // Return the first match ID if available
                if (matchArray.size() > 0) {
                    return matchArray.get(0).getAsString(); // Return the first match ID
                } else {
                    return null; // No match IDs available
                }
            } else {
                System.err.println("Error: " + connection.getResponseCode() + " - " + connection.getResponseMessage());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // In case of an error, return null
        }
    }
}
