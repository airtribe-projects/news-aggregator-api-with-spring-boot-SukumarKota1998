package com.airtribe.news_aggregator.dto;

public class GNewsSourceBean {

    private String id;
    private String name;
    private String url;
    private String country;

    public GNewsSourceBean(String id, String name, String url, String country) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.country = country;
    }

    public GNewsSourceBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
