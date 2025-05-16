package com.suleymanuysal.moviesapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.suleymanuysal.moviesapp.fragment.FragmentEpisodes;
import com.suleymanuysal.moviesapp.fragment.MoreDetailsForMovieFragment;
import com.suleymanuysal.moviesapp.fragment.RelatedMoviesFragment;

public class ViewPagerDetailAdapter extends FragmentStateAdapter {

    private int movieId;
    private int tvId;
    private String activity;
    private int seasonNumber;
    private String movieOrTv;


    public ViewPagerDetailAdapter(@NonNull FragmentActivity fragmentActivity, int movieId, int tvId, String activity, int seasonNumber, String movieOrTv) {
        super(fragmentActivity);
        this.movieId = movieId;
        this.activity = activity;
        this.tvId = tvId;
        this.seasonNumber = seasonNumber;
        this.movieOrTv = movieOrTv;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                switch (activity) {
                    case "normal":
                        return RelatedMoviesFragment.newInstance(movieId, tvId, activity, "dummy");

                    case "tv":
                        return FragmentEpisodes.newInstance(tvId, seasonNumber, activity);

                    case "myList":

                        if (movieOrTv.equals("movie")) {
                            return RelatedMoviesFragment.newInstance(movieId, tvId, activity, "movie");

                        } else if (movieOrTv.equals("tvs")) {
                            return FragmentEpisodes.newInstance(tvId, seasonNumber, activity);

                        }

                    case "search":

                        if (movieOrTv.equals("movie")) {
                            return RelatedMoviesFragment.newInstance(movieId, tvId, activity, "movie");

                        } else if (movieOrTv.equals("tvs")) {
                            return FragmentEpisodes.newInstance(tvId, seasonNumber, activity);

                        }

                        break;
                }
            case 1:
                switch (activity) {
                    case "normal":
                        return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "dummy");

                    case "tv":
                        return RelatedMoviesFragment.newInstance(movieId, tvId, activity, "dummy");

                    case "myList":

                        if (movieOrTv.equals("movie")) {
                            return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "movie");

                        } else if (movieOrTv.equals("tvs")) {
                            return RelatedMoviesFragment.newInstance(movieId, tvId, activity, "mtv");
                        }

                    case "search":

                        if (movieOrTv.equals("movie")) {
                            return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "movie");

                        } else if (movieOrTv.equals("tvs")) {
                            return RelatedMoviesFragment.newInstance(movieId, tvId, activity, "mtv");
                        }

                        break;
                }

            case 2:
                switch (activity) {
                    case "normal":
                        return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "dummy");

                    case "tv":

                        return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "dummy");

                    case "myList":

                        if (movieOrTv.equals("movie")) {
                            return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "movie");

                        } else if (movieOrTv.equals("tvs")) {
                            return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "mtv");
                        }

                    case "search":

                        if (movieOrTv.equals("movie")) {
                            return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "movie");

                        } else if (movieOrTv.equals("tvs")) {
                            return MoreDetailsForMovieFragment.newInstance(movieId, tvId, activity, "mtv");
                        }

                        break;
                }

            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {

        switch (activity) {
            case "normal":
                return 2;

            case "myList":
                if (movieOrTv.equals("movie")) {
                    return 2;
                } else if (movieOrTv.equals("tvs")) {
                    return 3;
                }
                break;

            case "search":
                if (movieOrTv.equals("movie")) {
                    return 2;
                } else if (movieOrTv.equals("tvs")) {
                    return 3;
                }
                break;

            case "tv":
                return 3;
        }
        return 3;
    }

}
