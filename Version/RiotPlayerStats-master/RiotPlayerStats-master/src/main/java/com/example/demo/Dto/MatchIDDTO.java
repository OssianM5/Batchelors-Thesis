package com.example.demo.Dto;


public class MatchIDDTO {
    private Integer ID;
    private String matchID;
    private String puuid0;
    private String puuid1;
    private String puuid2;
    private String puuid3;
    private String puuid4;
    private String puuid5;
    private String puuid6;
    private String puuid7;
    private String puuid8;
    private String puuid9;

    public MatchIDDTO(Integer ID, String matchID, String puuid0, String puuid1, String puuid2, String puuid3, String puuid4, String puuid5, String puuid6, String puuid7, String puuid8, String puuid9) {
        this.ID = ID;
        this.matchID = matchID;
        this.puuid0 = puuid0;
        this.puuid1 = puuid1;
        this.puuid2 = puuid2;
        this.puuid3 = puuid3;
        this.puuid4 = puuid4;
        this.puuid5 = puuid5;
        this.puuid6 = puuid6;
        this.puuid7 = puuid7;
        this.puuid8 = puuid8;
        this.puuid9 = puuid9;
    }

    public MatchIDDTO() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getPuuid0() {
        return puuid0;
    }

    public void setPuuid0(String puuid0) {
        this.puuid0 = puuid0;
    }

    public String getPuuid1() {
        return puuid1;
    }

    public void setPuuid1(String puuid1) {
        this.puuid1 = puuid1;
    }

    public String getPuuid2() {
        return puuid2;
    }

    public void setPuuid2(String puuid2) {
        this.puuid2 = puuid2;
    }

    public String getPuuid3() {
        return puuid3;
    }

    public void setPuuid3(String puuid3) {
        this.puuid3 = puuid3;
    }

    public String getPuuid4() {
        return puuid4;
    }

    public void setPuuid4(String puuid4) {
        this.puuid4 = puuid4;
    }

    public String getPuuid5() {
        return puuid5;
    }

    public void setPuuid5(String puuid5) {
        this.puuid5 = puuid5;
    }

    public String getPuuid6() {
        return puuid6;
    }

    public void setPuuid6(String puuid6) {
        this.puuid6 = puuid6;
    }

    public String getPuuid7() {
        return puuid7;
    }

    public void setPuuid7(String puuid7) {
        this.puuid7 = puuid7;
    }

    public String getPuuid8() {
        return puuid8;
    }

    public void setPuuid8(String puuid8) {
        this.puuid8 = puuid8;
    }

    public String getPuuid9() {
        return puuid9;
    }

    public void setPuuid9(String puuid9) {
        this.puuid9 = puuid9;
    }

    @Override
    public String toString() {
        return "MatchIDDTO{" +
                "ID=" + ID +
                ", matchID='" + matchID + '\'' +
                ", puuid0='" + puuid0 + '\'' +
                ", puuid1='" + puuid1 + '\'' +
                ", puuid2='" + puuid2 + '\'' +
                ", puuid3='" + puuid3 + '\'' +
                ", puuid4='" + puuid4 + '\'' +
                ", puuid5='" + puuid5 + '\'' +
                ", puuid6='" + puuid6 + '\'' +
                ", puuid7='" + puuid7 + '\'' +
                ", puuid8='" + puuid8 + '\'' +
                ", puuid9='" + puuid9 + '\'' +
                '}';
    }
}
