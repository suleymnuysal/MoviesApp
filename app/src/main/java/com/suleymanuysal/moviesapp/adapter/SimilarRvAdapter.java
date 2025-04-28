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
import com.suleymanuysal.moviesapp.databinding.DetailSimilarMoviesRowBinding;
import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.model.tv.Tv;

import java.util.List;

public class SimilarRvAdapter extends RecyclerView.Adapter<SimilarRvAdapter.SimilarWidgetsHolder> {

    private Context context;
    private List<Movie> similarMovies;
    private List<Tv> similarTvs;
    private DetailSimilarMoviesRowBinding binding;
    private String posterPath;


    public SimilarRvAdapter(Context context, List<Movie> similarMovies,List<Tv> similarTvs) {
        this.context = context;
        if(similarMovies != null){
            this.similarMovies = similarMovies;
        } else if (similarTvs != null) {
            this.similarTvs = similarTvs;
        }


    }

    @NonNull
    @Override
    public SimilarWidgetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DetailSimilarMoviesRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new SimilarWidgetsHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull SimilarWidgetsHolder holder, int position) {
        holder.setIsRecyclable(false);

        if(similarMovies != null){
            Movie similarMovie = similarMovies.get(position);

            posterPath = similarMovie.getPoster_path();

            binding.similarTopRatedCardView.setOnClickListener(v -> {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "normal");
                intent.putExtra("MovieId",similarMovies.get(position));
                context.startActivity(intent);
            });


        } else if (similarTvs != null) {
            Tv similarMovie = similarTvs.get(position);

            posterPath = similarMovie.getPoster_path();

            binding.similarTopRatedCardView.setOnClickListener(v -> {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "tv");
                intent.putExtra("tv_id", similarTvs.get(position));
                context.startActivity(intent);
            });


        }


        String imageUrl = posterPath;
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + imageUrl + "")
                .placeholder(R.drawable.place_holder_tv)
                .into(binding.similarImageView);


    }


    @Override
    public int getItemCount() {
        if(similarMovies != null){

            if (similarMovies.size() >= 12) {
                return 12;
            } else {
                return similarMovies.size();
            }

        } else if (similarTvs != null) {

            if (similarTvs.size() >= 12) {
                return 12;
            } else {
                return similarTvs.size();
            }
        }else {
            return 0;
        }



    }

    public class SimilarWidgetsHolder extends RecyclerView.ViewHolder {
        DetailSimilarMoviesRowBinding binding;

        public SimilarWidgetsHolder(@NonNull DetailSimilarMoviesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
