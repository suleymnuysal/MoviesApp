package com.suleymanuysal.moviesapp.model.movie;

public class MovieVideos {
    private String name;
    private String key;
    private String site;
    private String type;
    private String id;

    public MovieVideos(String name, String key, String site, String type, String id) {
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
