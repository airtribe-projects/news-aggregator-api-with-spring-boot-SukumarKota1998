package com.airtribe.news_aggregator.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String emailId;

    @Column(nullable = false)
    private String password;

    private String role;

    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPreferences> userPreferences;

    public User(long id, String userName, String emailId, String password, String role, boolean active, List<UserPreferences> userPreferences) {
        this.id = id;
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.role = role;
        this.active = active;
        this.userPreferences = userPreferences;
    }

    public User(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<UserPreferences> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<UserPreferences> userPreferences) {
        this.userPreferences = userPreferences;
    }
}
