package com.example.demo.Dto;

public class PuuidMatchDTO {

    private Integer id;
    private String puuid;
    private String matchId;

    public PuuidMatchDTO(Integer id, String puuid, String matchId) {
        this.id = id;
        this.puuid = puuid;
        this.matchId = matchId;
    }

    public PuuidMatchDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "PuuidMatchDTO{" +
                "id=" + id +
                ", puuid='" + puuid + '\'' +
                ", matchId='" + matchId + '\'' +
                '}';
    }
}
