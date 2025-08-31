package com.airtribe.news_aggregator.dto;

import java.time.LocalDateTime;

public class GNewsArticleBean {

    private String id;
    private String title;
    private String description;
    private String content;
    private String url;
    private String image;
    private LocalDateTime publishedAt;
    private String lang;
    private GNewsSourceBean source;

    public GNewsArticleBean(String id, String title, String description, String content, String url, String image,
                            LocalDateTime publishedAt, String lang, GNewsSourceBean source) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.image = image;
        this.publishedAt = publishedAt;
        this.lang = lang;
        this.source = source;
    }

    public GNewsArticleBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public GNewsSourceBean getSource() {
        return source;
    }

    public void setSource(GNewsSourceBean gNewsSourceBean) {
        this.source = gNewsSourceBean;
    }
}
