package com.suleymanuysal.moviesapp.model.tv;

import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.movie.MovieGenres;

import java.io.Serializable;
import java.util.List;

public class TvDetail implements Serializable {

    @SerializedName("genres")
    List<MovieGenres> genres;
    @SerializedName("seasons")
    List<Seasons> seasons;
    @SerializedName("original_name")
    private String original_name;
    @SerializedName("vote_average")
    private double vote_average;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("overview")
    private String overview;
    @SerializedName("last_air_date")
    private String last_air_date;
    @SerializedName("status")
    private String status;
    @SerializedName("id")
    private int id;
    @SerializedName("in_production")
    private boolean in_production;
    @SerializedName("number_of_seasons")
    private int number_of_seasons;

    public TvDetail(String original_name, double vote_average, String first_air_date, String original_language, String overview, String last_air_date, String status, int id, boolean in_production, int number_of_seasons, List<MovieGenres> genres, List<Seasons> seasons) {
        this.original_name = original_name;
        this.vote_average = vote_average;
        this.first_air_date = first_air_date;
        this.original_language = original_language;
        this.overview = overview;
        this.last_air_date = last_air_date;
        this.status = status;
        this.id = id;
        this.in_production = in_production;
        this.number_of_seasons = number_of_seasons;
        this.genres = genres;
        this.seasons = seasons;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOverview() {
        return overview;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public List<MovieGenres> getGenres() {
        return genres;
    }
}
