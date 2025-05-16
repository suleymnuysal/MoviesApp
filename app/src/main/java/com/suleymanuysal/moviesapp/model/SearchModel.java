package com.suleymanuysal.moviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchModel implements Serializable {

    private String media_type;
    //For Movie

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

    //For Tv

    private String first_air_date;
    private List<String> origin_country;
    private String name;
    private String original_name;

    //For Person

    private String profile_path;

    public SearchModel(String media_type, String poster_path, boolean adult, String overview, String release_date, List<Integer> genre_ids, int id, String original_title, String original_language, String title, String backdrop_path, double popularity, int vote_count, boolean video, double vote_average, String first_air_date, List<String> origin_country, String name, String original_name,String profile_path) {
        this.media_type = media_type;
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
        this.first_air_date = first_air_date;
        this.origin_country = origin_country;
        this.name = name;
        this.original_name = original_name;
        this.profile_path = profile_path;
    }


    public String getMedia_type() {
        return media_type;
    }

    public String getProfile_path() {
        return profile_path;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }
}
