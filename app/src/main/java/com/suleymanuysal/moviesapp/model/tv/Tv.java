package com.suleymanuysal.moviesapp.model.tv;


import java.io.Serializable;
import java.util.List;

public class Tv implements Serializable {

    private String poster_path;
    private double popularity;
    private int id;
    private double vote_average;
    private String overview;
    private String first_air_date;
    private List<String> origin_country;
    private List<Integer> genre_ids;
    private String original_language;
    private int vote_count;
    private String name;
    private String original_name;


    public Tv(String poster_path, double popularity, int id, double vote_average, String overview, String first_air_date, List<String> origin_country, List<Integer> genre_ids, String original_language, int vote_count, String name, String original_name) {
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.id = id;
        this.vote_average = vote_average;
        this.overview = overview;
        this.first_air_date = first_air_date;
        this.origin_country = origin_country;
        this.genre_ids = genre_ids;
        this.original_language = original_language;
        this.vote_count = vote_count;
        this.name = name;
        this.original_name = original_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

}

