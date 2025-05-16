package com.suleymanuysal.moviesapp.model.results;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.SearchModel;
import com.suleymanuysal.moviesapp.model.movie.Movie;

import java.util.List;

public class SearchResult {
    @SerializedName("results")
    @Expose
    private List<SearchModel> searchedList;


    @SerializedName("page")
    @Expose
    private int page;


    @SerializedName("total_results")
    @Expose
    private int total_results;


    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    public SearchResult(List<SearchModel> searchedList, int page, int total_results, int total_pages) {
        this.searchedList = searchedList;
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public List<SearchModel> getSearchedList() {
        return searchedList;
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
