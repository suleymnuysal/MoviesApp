package com.suleymanuysal.moviesapp.model.movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieDetail implements Serializable {

    @SerializedName("genres")
    List<MovieGenres> genres;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("overview")
    private String overview;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("video")
    private boolean video;
    @SerializedName("id")
    private int id;

    public MovieDetail(String original_title, int runtime, double vote_average, String release_date, String original_language, String overview, boolean adult, boolean video, int id, List<MovieGenres> genres) {
        this.original_title = original_title;
        this.runtime = runtime;
        this.release_date = release_date;
        this.original_language = original_language;
        this.overview = overview;
        this.adult = adult;
        this.vote_average = vote_average;
        this.id = id;
        this.genres = genres;
        this.video = video;


    }

    public boolean isVideo() {
        return video;
    }

    public List<MovieGenres> getGenres() {
        return genres;
    }

    public double getVote_average() {
        return vote_average;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }

}
