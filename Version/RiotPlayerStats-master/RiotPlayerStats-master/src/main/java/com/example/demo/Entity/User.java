package com.example.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Optional: explicitly specify table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // Maps to user_id

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String puuid;

    @Column(nullable = false, length = 50)
    private String summonerName;

    @Column(nullable = false, length = 10)
    private String tagline;

    public User(Integer userId, String email, String password, String puuid, String summonerName, String tagline) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.puuid = puuid;
        this.summonerName = summonerName;
        this.tagline = tagline;
    }

    public User(String email, String summonerName, String tagline) {
        this.email = email;
        this.summonerName = summonerName;
        this.tagline = tagline;
    }

    public User() {
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
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", puuid='" + puuid + '\'' +
                ", summonerName='" + summonerName + '\'' +
                ", tagline='" + tagline + '\'' +
                '}';
    }
}
