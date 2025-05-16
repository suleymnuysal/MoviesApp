package com.suleymanuysal.moviesapp.model.movie;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {

    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private double popularity;
    private int vote_count;
    private boolean video;
    private double vote_average;

    public Movie(String poster_path, boolean adult, String overview, String release_date, List<Integer> genre_ids, int id, String original_title, String original_language, String title, String backdrop_path, double popularity, int vote_count, boolean video, double vote_average) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }
}
