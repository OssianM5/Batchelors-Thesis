package com.example.demo.Dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UserDTO {

    private Integer userId; // Maps to user_id

    private String email;

    private String password;

    private String puuid;

    private String summonerName;

    private String tagline;

    public UserDTO(Integer userId, String email, String password, String puuid, String summonerName, String tagline) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.puuid = puuid;
        this.summonerName = summonerName;
        this.tagline = tagline;
    }

    public UserDTO() {
    }

    public UserDTO(String email, String summonerName, String tagline) {
        this.email = email;
        this.summonerName = summonerName;
        this.tagline = tagline;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", puuid='" + puuid + '\'' +
                ", summonerName='" + summonerName + '\'' +
                ", tagline='" + tagline + '\'' +
                '}';
    }
}
