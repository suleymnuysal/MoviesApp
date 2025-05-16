package com.suleymanuysal.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.activity.DetailActivity;
import com.suleymanuysal.moviesapp.databinding.NewAndHotRowBinding;
import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.model.tv.Tv;
import com.suleymanuysal.moviesapp.model.tv.TvGenres;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class NewAndHotUpcomingRvAdapter extends RecyclerView.Adapter<NewAndHotUpcomingRvAdapter.UpcomingMovieViewHolder> {

    private NewAndHotRowBinding binding;
    private List<Movie> upcomingMovieList;
    private List<Tv> upcomingTvList;
    private Context context;
    private String name;
    private String overView;
    private String posterPath;
    private String releaseDate;
    private List<Integer> genreIds;


    public NewAndHotUpcomingRvAdapter(List<Movie> upcomingMovieList,List<Tv> upcomingTvList, Context context) {
        this.context = context;

        if (upcomingMovieList != null){
            this.upcomingMovieList = upcomingMovieList;
        }else if (upcomingTvList != null) {
            this.upcomingTvList = upcomingTvList;
        }


    }

    @NonNull
    @Override
    public UpcomingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = NewAndHotRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new UpcomingMovieViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull UpcomingMovieViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        if (upcomingMovieList != null){
            Movie upcomingMovie =  upcomingMovieList.get(position);

            name = upcomingMovie.getOriginal_title();
            overView = upcomingMovie.getOverview();
            posterPath = upcomingMovie.getPoster_path();
            genreIds = upcomingMovie.getGenre_ids();
            releaseDate = upcomingMovie.getRelease_date();

            binding.upcomingInfo.setOnClickListener(v -> {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "normal");
                intent.putExtra("MovieId", upcomingMovie);
                context.startActivity(intent);


            });

            binding.upcomingImageView.setOnClickListener(v -> {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "normal");
                intent.putExtra("MovieId", upcomingMovie);
                context.startActivity(intent);
            });



        }else if (upcomingTvList != null) {
            Tv upcomingMovie = upcomingTvList.get(position);

            name = upcomingMovie.getName();
            overView = upcomingMovie.getOverview();
            posterPath = upcomingMovie.getPoster_path();
            genreIds = upcomingMovie.getGenre_ids();
            releaseDate = upcomingMovie.getFirst_air_date();

            binding.upcomingInfo.setOnClickListener(v -> {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "tv");
                intent.putExtra("tv_id", upcomingMovie);
                context.startActivity(intent);


            });

            binding.upcomingImageView.setOnClickListener(v -> {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "tv");
                intent.putExtra("tv_id", upcomingMovie);
                context.startActivity(intent);
                
            });

        }

        String imageUrl = posterPath;
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + imageUrl + "")
                .placeholder(R.drawable.movie_place_holder)
                .into(binding.upcomingImageView);

        binding.upcomingName.setText(name);

        binding.upcomingOverview.setText(overView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            Date date = simpleDateFormat.parse(releaseDate);

            binding.textViewRelaseDate.setText(date.toString().substring(4, 10));

            binding.upcomingRelaseDay.setText("Coming " + date.toString().substring(0, 3));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        ArrayList<String> genres = new ArrayList<>();
        for (int m : genreIds
                ) {

            TvGenres tvGenres = new TvGenres();
            genres.add(tvGenres.getGenresForTv(m));

        }

        String genre = genres.toString().substring(1, (genres.toString().length() - 1));

        binding.upcomingGenres.setText(genre);


    }

    @Override
    public int getItemCount() {

        if (upcomingMovieList != null){
            return upcomingMovieList.size();
        }else if (upcomingTvList != null) {
            return upcomingTvList.size();
        }else {
            return 0;
        }

    }

    public static class UpcomingMovieViewHolder extends RecyclerView.ViewHolder {
        NewAndHotRowBinding binding;

        public UpcomingMovieViewHolder(@NonNull NewAndHotRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
