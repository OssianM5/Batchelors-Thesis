package com.example.demo.Entity;

import jakarta.persistence.*;


@Entity
@Table(name = "puuidmatch")
public class PuuidMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String puuid;

    @Column(nullable = false, length = 255)
    private String matchId;

    public PuuidMatch(Integer id, String puuid, String matchId) {
        this.id = id;
        this.puuid = puuid;
        this.matchId = matchId;
    }


    public PuuidMatch() {
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
        return "PuuidMatch{" +
                "id=" + id +
                ", puuid='" + puuid + '\'' +
                ", matchId='" + matchId + '\'' +
                '}';
    }


}
