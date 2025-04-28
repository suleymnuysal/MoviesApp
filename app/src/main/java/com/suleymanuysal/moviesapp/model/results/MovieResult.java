package com.suleymanuysal.moviesapp.model.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.movie.Movie;

import java.util.List;

public class MovieResult {

    @SerializedName("results")
    @Expose
    private List<Movie> movieList;


    @SerializedName("page")
    @Expose
    private int page;


    @SerializedName("total_results")
    @Expose
    private int total_results;


    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public MovieResult(List<Movie> movieList, int page, int total_results, int total_pages) {
        this.movieList = movieList;
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
