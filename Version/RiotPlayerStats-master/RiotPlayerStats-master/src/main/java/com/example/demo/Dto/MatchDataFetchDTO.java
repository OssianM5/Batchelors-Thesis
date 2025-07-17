package com.example.demo.Dto;

public class MatchDataFetchDTO {
    String puuid;
    String matchId;

    public MatchDataFetchDTO(String puuid, String matchId) {
        this.puuid = puuid;
        this.matchId = matchId;
    }

    public MatchDataFetchDTO() {
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return "MatchDataFetchDTO{" +
                "puuid='" + puuid + '\'' +
                ", matchId='" + matchId + '\'' +
                '}';
    }
}
