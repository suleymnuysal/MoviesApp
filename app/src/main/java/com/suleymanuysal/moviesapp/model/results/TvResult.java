package com.suleymanuysal.moviesapp.model.results;

import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.tv.Tv;

import java.util.List;

public class TvResult {
    @SerializedName("results")
    private List<Tv> tvList;

    @SerializedName("page")
    private int page;


    @SerializedName("total_results")
    private int total_results;

    @SerializedName("total_pages")
    private int total_pages;

    public TvResult(List<Tv> popularTvList, int page, int total_results, int total_pages) {
        this.tvList = popularTvList;
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public List<Tv> getTvList() {
        return tvList;
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
