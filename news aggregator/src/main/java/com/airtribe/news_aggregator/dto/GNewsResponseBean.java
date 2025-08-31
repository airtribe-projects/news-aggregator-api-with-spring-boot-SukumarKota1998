package com.airtribe.news_aggregator.dto;

import java.util.List;

public class GNewsResponseBean {

    private long totalArticles;

    private List<GNewsArticleBean> articles;

    public GNewsResponseBean(long totalArticles, List<GNewsArticleBean> articles) {
        this.totalArticles = totalArticles;
        this.articles = articles;
    }

    public GNewsResponseBean() {
    }

    public long getTotalArticles() {
        return totalArticles;
    }

    public void setTotalArticles(long totalArticles) {
        this.totalArticles = totalArticles;
    }

    public List<GNewsArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<GNewsArticleBean> articles) {
        this.articles = articles;
    }
}
