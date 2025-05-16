package com.suleymanuysal.moviesapp.model.tv;

public class Episodes {
    private String air_date;
    private String name;
    private String overview;
    private String production_code;
    private String still_path;
    private int episode_number;
    private int id;
    private int season_number;
    private double vote_average;
    private int vote_count;
    private int runtime;

    public Episodes(String air_date, String name, String overview, String production_code, String still_path, int episode_number, int id, int season_number, double vote_average, int vote_count, int runtime) {
        this.air_date = air_date;
        this.name = name;
        this.overview = overview;
        this.production_code = production_code;
        this.still_path = still_path;
        this.episode_number = episode_number;
        this.id = id;
        this.season_number = season_number;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.runtime = runtime;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getAir_date() {
        return air_date;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getProduction_code() {
        return production_code;
    }

    public String getStill_path() {
        return still_path;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public int getId() {
        return id;
    }

    public int getSeason_number() {
        return season_number;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }
}
