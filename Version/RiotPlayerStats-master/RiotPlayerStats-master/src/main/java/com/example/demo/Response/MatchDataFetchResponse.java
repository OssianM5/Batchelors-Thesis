package com.example.demo.Response;

public class MatchDataFetchResponse {
    private Integer id;
    private String matchId;
    private String puuid;
    private int goldEarned;
    private int damageDealt;
    private int damageTaken;
    private int kills;
    private int deaths;
    private int assists;
    private int visionScore;

    public MatchDataFetchResponse(Integer id, String matchId, String puuid, int goldEarned, int damageDealt, int damageTaken, int kills, int deaths, int assists, int visionScore) {
        this.id = id;
        this.matchId = matchId;
        this.puuid = puuid;
        this.goldEarned = goldEarned;
        this.damageDealt = damageDealt;
        this.damageTaken = damageTaken;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.visionScore = visionScore;
    }

    public MatchDataFetchResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(int goldEarned) {
        this.goldEarned = goldEarned;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getVisionScore() {
        return visionScore;
    }

    public void setVisionScore(int visionScore) {
        this.visionScore = visionScore;
    }

    @Override
    public String toString() {
        return "MatchDataFetchResponse{" +
                "id=" + id +
                ", matchId='" + matchId + '\'' +
                ", puuid='" + puuid + '\'' +
                ", goldEarned=" + goldEarned +
                ", damageDealt=" + damageDealt +
                ", damageTaken=" + damageTaken +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", assists=" + assists +
                ", visionScore=" + visionScore +
                '}';
    }
}
