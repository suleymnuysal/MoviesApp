package com.suleymanuysal.moviesapp.model.movie;

public class MovieCastAndCrew {
    //Cast
    private boolean adult;
    private int gender;
    private int id;
    private String known_for_department;
    private String name;
    private String original_name;
    private double popularity;
    private String profile_path;
    private int cast_id;
    private String character;
    private String credit_id;
    private int order;

    //Crew

    private int crew_id;
    private String crew_known_for_department;
    private String crew_name;
    private String crew_original_name;
    private double crew_popularity;
    private String crew_credit_id;
    private String department;
    private String job;
    private String crew_profile_path;


    public MovieCastAndCrew(boolean adult, int gender, int id, String known_for_department, String name, String original_name, double popularity, String profile_path, int cast_id, String character, String credit_id, int order, int crew_id, String crew_known_for_department, String crew_name, String crew_original_name, double crew_popularity, String crew_credit_id, String department, String job, String crew_profile_path) {
        this.adult = adult;
        this.gender = gender;
        this.id = id;
        this.known_for_department = known_for_department;
        this.name = name;
        this.original_name = original_name;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.order = order;
        this.crew_id = crew_id;
        this.crew_known_for_department = crew_known_for_department;
        this.crew_name = crew_name;
        this.crew_original_name = crew_original_name;
        this.crew_popularity = crew_popularity;
        this.crew_credit_id = crew_credit_id;
        this.department = department;
        this.job = job;
        this.crew_profile_path = crew_profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getCrew_profile_path() {
        return crew_profile_path;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public int getCast_id() {
        return cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public int getOrder() {
        return order;
    }

    public int getCrew_id() {
        return crew_id;
    }

    public String getCrew_known_for_department() {
        return crew_known_for_department;
    }

    public String getCrew_name() {
        return crew_name;
    }

    public String getCrew_original_name() {
        return crew_original_name;
    }

    public double getCrew_popularity() {
        return crew_popularity;
    }

    public String getCrew_credit_id() {
        return crew_credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public String getJob() {
        return job;
    }
}
