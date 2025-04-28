package com.suleymanuysal.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import com.suleymanuysal.moviesapp.activity.DetailActivity;
import com.suleymanuysal.moviesapp.databinding.TvCardViewRowBinding;
import com.suleymanuysal.moviesapp.model.tv.Tv;

import java.util.List;

public class TvShowsCardViewRvAdapter extends RecyclerView.Adapter<TvShowsCardViewRvAdapter.AiringTodayViewHolder> {

    TvCardViewRowBinding binding;
    Context context;
    private List<Tv> tvList;

    public TvShowsCardViewRvAdapter(List<Tv> tvList, Context context) {
        this.tvList = tvList;
        this.context = context;
    }

    @NonNull
    @Override
    public AiringTodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TvCardViewRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new AiringTodayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AiringTodayViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        Tv tv = tvList.get(position);

        String imageUrlr = tv.getPoster_path();
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + imageUrlr + "")
                .into(binding.imageViewTv);

        binding.cardViewTv.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("info", "tv");
            intent.putExtra("tv_id", tv);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    public static class AiringTodayViewHolder extends RecyclerView.ViewHolder {
        TvCardViewRowBinding binding;

        public AiringTodayViewHolder(@NonNull TvCardViewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
