package com.suleymanuysal.moviesapp.model.tv;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TvSeasonDetail implements Serializable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("episodes")
    private List<Episodes> episodes;

    @SerializedName("air_date")
    private String air_date;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("season_number")
    private int season_number;

    public TvSeasonDetail(String _id, List<Episodes> episodes, String air_date, String name, String overview, int id, String poster_path, int season_number) {
        this._id = _id;
        this.episodes = episodes;
        this.air_date = air_date;
        this.name = name;
        this.overview = overview;
        this.id = id;
        this.poster_path = poster_path;
        this.season_number = season_number;
    }

    public String get_id() {
        return _id;
    }

    public List<Episodes> getEpisodes() {
        return episodes;
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

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getSeason_number() {
        return season_number;
    }
}
