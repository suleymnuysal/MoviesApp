package com.suleymanuysal.moviesapp.model.results;

import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.movie.MovieVideos;

import java.io.Serializable;
import java.util.List;

public class MovieVideosResult implements Serializable {
    @SerializedName("results")
    private List<MovieVideos> getMovieVideos;

    @SerializedName("id")
    private int id;


    public MovieVideosResult(List<MovieVideos> getMovieVideos, int id) {
        this.getMovieVideos = getMovieVideos;
        this.id = id;
    }

    public List<MovieVideos> getGetMovieVideos() {
        return getMovieVideos;
    }

    public int getId() {
        return id;
    }
}
