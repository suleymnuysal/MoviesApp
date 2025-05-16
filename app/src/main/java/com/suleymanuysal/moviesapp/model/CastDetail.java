package com.suleymanuysal.moviesapp.model;

import com.google.gson.annotations.SerializedName;

public class CastDetail {

    @SerializedName("name")
    private String name;

    @SerializedName("biography")
    private String biography;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("place_of_birth")
    private String place_of_birth;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("profile_path")
    private String profile_path;

    public CastDetail(String name, String biography, String birthday, String place_of_birth, double popularity, String profile_path) {
        this.name = name;
        this.biography = biography;
        this.birthday = birthday;
        this.place_of_birth = place_of_birth;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
