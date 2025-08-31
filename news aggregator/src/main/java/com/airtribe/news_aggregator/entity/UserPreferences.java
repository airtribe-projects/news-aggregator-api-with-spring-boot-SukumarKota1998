package com.airtribe.news_aggregator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private NewsCategory category;

    private String source;

    private String language;

    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public UserPreferences(long id, NewsCategory category, String source, String language, boolean isActive, User user) {
        this.id = id;
        this.category = category;
        this.source = source;
        this.language = language;
        this.isActive = isActive;
        this.user = user;
    }

    public UserPreferences(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
