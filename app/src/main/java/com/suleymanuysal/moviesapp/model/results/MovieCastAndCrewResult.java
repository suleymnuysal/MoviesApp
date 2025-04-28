package com.suleymanuysal.moviesapp.model.results;

import com.google.gson.annotations.SerializedName;
import com.suleymanuysal.moviesapp.model.movie.MovieCastAndCrew;

import java.io.Serializable;
import java.util.List;

public class MovieCastAndCrewResult implements Serializable {

    @SerializedName("cast")
    private List<MovieCastAndCrew> getCasts;

    @SerializedName("crew")
    private List<MovieCastAndCrew> getCrews;

    @SerializedName("id")
    private int id;

    public MovieCastAndCrewResult(List<MovieCastAndCrew> getCasts, List<MovieCastAndCrew> getCrews, int id) {
        this.getCasts = getCasts;
        this.getCrews = getCrews;
        this.id = id;
    }

    public List<MovieCastAndCrew> getGetCasts() {
        return getCasts;
    }

    public List<MovieCastAndCrew> getGetCrews() {
        return getCrews;
    }

    public int getId() {
        return id;
    }
}
