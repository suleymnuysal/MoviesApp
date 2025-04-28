package com.suleymanuysal.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import com.suleymanuysal.moviesapp.activity.DetailActivity;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.databinding.HomeCardViewRowBinding;

import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.utils.ShowBottomSheet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class HomeCardViewRvAdapter extends RecyclerView.Adapter<HomeCardViewRvAdapter.WidgetsHolder> {

    private Context context;
    private List<Movie> movieList;
    private HomeCardViewRowBinding binding;
    private HomeBottomSheetDesignBinding mbinding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ArrayList<Integer> idList = new ArrayList<>();

    public HomeCardViewRvAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;

    }

    @NonNull
    @Override
    public WidgetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = HomeCardViewRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WidgetsHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull WidgetsHolder holder, int position) {
        holder.setIsRecyclable(false);

        Movie movie = movieList.get(position);

        Double d = (movie.getVote_average() * 5) / 10;
        Float f = new Float(d);

        binding.ratingBarHome.setRating(f);

        String imageUrl = movie.getPoster_path();
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500"+imageUrl+"")
                .into(binding.imageViewHome);


        binding.homeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", "normal");
                intent.putExtra("MovieId", movie);
                context.startActivity(intent);
            }
        });


        binding.imageViewHomeCardOptionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowBottomSheet.showBottomSheet(movie, context, mbinding,"normal",v,0,"movieId","MyListMovies");

            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class WidgetsHolder extends RecyclerView.ViewHolder {

        HomeCardViewRowBinding binding;

        public WidgetsHolder(@NonNull HomeCardViewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
