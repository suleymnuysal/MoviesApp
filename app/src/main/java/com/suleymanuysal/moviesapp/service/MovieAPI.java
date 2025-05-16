package com.suleymanuysal.moviesapp.service;

import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.model.CastDetail;

import com.suleymanuysal.moviesapp.model.movie.MovieDetail;
import com.suleymanuysal.moviesapp.model.results.MovieCastAndCrewResult;
import com.suleymanuysal.moviesapp.model.results.MovieVideosResult;
import com.suleymanuysal.moviesapp.model.results.MovieResult;
import com.suleymanuysal.moviesapp.model.results.SearchResult;
import com.suleymanuysal.moviesapp.model.results.TvResult;
import com.suleymanuysal.moviesapp.model.results.TvVideosResult;
import com.suleymanuysal.moviesapp.model.tv.TvDetail;
import com.suleymanuysal.moviesapp.model.tv.TvSeasonDetail;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {


    //------------------------------ For Movies ------------------------------


    @GET("now_playing?")
    Observable<MovieResult> getAllNowPlayingMovies(
            @Query("api_key") String api_key,
            @Query("page") int page

    );

    @GET("https://api.themoviedb.org/3/trending/movie/day?")
    Observable<MovieResult> getAllTrendingMoviesNow(
            @Query("api_key") String api_key,
            @Query("page") int page

    );

    @GET(Credentials.MOVIE_BASE_URL + "popular?")
    Observable<MovieResult> getAllPopularMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET(Credentials.DISCOVER_MOVIE_BASE_URL+"?&include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc")
    Observable<MovieResult> getAllMovieCategories(
            @Query("api_key") String api_key,
            @Query("with_genres") String genre
    );

    @GET("upcoming?")
    Observable<MovieResult> getAllUpcomingMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET("top_rated?")
    Observable<MovieResult> getAllTopRatedMovies(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET(Credentials.SEARCH_BASE_URL + "multi?")
    Observable<SearchResult> getAllMultiSearched(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("{movie_id}?")
    Observable<MovieDetail> getMovieDetail(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("{movie_id}/credits?")
    Observable<MovieCastAndCrewResult> getCastAndCrew(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey);

    @GET("{movie_id}/videos?")
    Observable<MovieVideosResult> getAllMovieVideos(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey);

    @GET("{movie_id}/similar?")
    Observable<MovieResult> getSimilarMovies(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey);

    @GET("{movie_id}/recommendations?")
    Observable<MovieResult> getRecommendationMovies(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey);


    //------------------------------ For TVs ------------------------------
    @GET(Credentials.TV_BASE_URL + "popular?&page=")
    Observable<TvResult> getAllPopularTvs(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET(Credentials.DISCOVER_TV_BASE_URL+"?&include_adult=false&include_null_first_air_dates=false&language=en-US&page=1&sort_by=popularity.desc")
    Observable<TvResult> getAllTvCategories(
            @Query("api_key") String api_key,
            @Query("with_genres") String genre
    );

    @GET(Credentials.TV_BASE_URL + "on_the_air?&page=")
    Observable<TvResult> getAllOnTheAirTvs(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET(Credentials.TV_BASE_URL + "top_rated?&page=")
    Observable<TvResult> getAllTopRatedTvs(
            @Query("api_key") String api_key,
            @Query("page") int page
    );


    @GET(Credentials.TV_BASE_URL + "airing_today?&page=")
    Observable<TvResult> getAllAiringTodayTvs(
            @Query("api_key") String api_key,
            @Query("page") int page
    );

    @GET(Credentials.TV_BASE_URL + "{tv_id}?")
    Observable<TvDetail> getTvDetail(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey
    );

    @GET(Credentials.TV_BASE_URL + "{tv_id}/videos?")
    Observable<TvVideosResult> getAllTvVideos(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey
    );

    @GET(Credentials.TV_BASE_URL + "{tv_id}/similar?")
    Observable<TvResult> getAllSimilarTvs(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey
    );

    @GET(Credentials.TV_BASE_URL + "{series_id}/recommendations?")
    Observable<TvResult> getAllRecommendedTvs(
            @Path("series_id") int tvId,
            @Query("api_key") String apiKey
    );

    @GET("https://api.themoviedb.org/3/trending/tv/day?")
    Observable<TvResult> getAllTrendingTvsNow(
            @Query("api_key") String api_key,
            @Query("page") int page

    );

    @GET(Credentials.TV_BASE_URL + "{tv_id}/credits?")
    Observable<MovieCastAndCrewResult> getCastAndCrewForTv(
            @Path("tv_id") int tvId,
            @Query("api_key") String apiKey
    );

    @GET(Credentials.TV_BASE_URL + "{tv_id}/season/{season_number}?")
    Observable<TvSeasonDetail> getTvSeasonEpisodes(
            @Path("tv_id") int tvId,
            @Path("season_number") int seasonNumber,
            @Query("api_key") String apiKey
    );

    @GET(Credentials.TV_BASE_URL + "{tv_id}/season/{season_number}/episode/{episode_number}/videos?")
    Observable<TvVideosResult> getTvEpisodeVideos(
            @Path("tv_id") int tvId,
            @Path("season_number") int seasonNumber,
            @Path("episode_number") int episodeNumber,
            @Query("api_key") String apiKey

    );


    @GET(Credentials.PERSON_BASE_URL+"{person_id}?")
    Observable<CastDetail> getPersonDetail(
            @Path("person_id") int personId,
            @Query("api_key") String apiKey
    );




}
