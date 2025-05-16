package com.suleymanuysal.moviesapp.model.results;

import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.movie.TvVideos;

import java.io.Serializable;
import java.util.List;

public class TvVideosResult implements Serializable {
    @SerializedName("results")
    private List<TvVideos> getTvVideos;

    @SerializedName("id")
    private int id;

    public TvVideosResult(List<TvVideos> getTvVideos, int id) {
        this.getTvVideos = getTvVideos;
        this.id = id;
    }

    public List<TvVideos> getTvVideos() {
        return getTvVideos;
    }

    public int getId() {
        return id;
    }
}
