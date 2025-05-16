package com.suleymanuysal.moviesapp.apiUtils;

import com.suleymanuysal.moviesapp.retrofitClient.RetrofitClient;
import com.suleymanuysal.moviesapp.service.MovieAPI;

public class ApiUtils {
    public static MovieAPI getMovieApi(String baseUrl) {
        return RetrofitClient.getRetrofitClient(baseUrl).create(MovieAPI.class);
    }

}
