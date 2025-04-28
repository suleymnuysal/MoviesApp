package com.suleymanuysal.moviesapp.model.movie;


import java.io.Serializable;

public class MyListMovieModel implements Serializable {
    private String posterPath;
    private String name;
    private String releaseDate;
    private long movieId;
    private String movieOrTv;
    private double voteAverage;

    public MyListMovieModel(String posterPath, String name, String releaseDate,long movieId,String movieOrTv,double voteAverage) {
        this.posterPath = posterPath;
        this.name = name;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.voteAverage = voteAverage;

    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getMovieOrTv() {
        return movieOrTv;
    }

    public void setMovieOrTv(String movieOrTv) {
        this.movieOrTv = movieOrTv;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
