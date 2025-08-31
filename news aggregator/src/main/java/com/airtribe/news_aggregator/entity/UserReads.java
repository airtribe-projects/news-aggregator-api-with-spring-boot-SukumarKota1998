package com.airtribe.news_aggregator.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserReads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    private boolean isFavorite;

    private LocalDateTime updatedAt;

    public UserReads(long id, User user, Article article, boolean isFavorite, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.article = article;
        this.isFavorite = isFavorite;
        this.updatedAt = updatedAt;
    }
    public UserReads(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
