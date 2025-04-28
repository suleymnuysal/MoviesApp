package com.suleymanuysal.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.activity.VideosActivity;
import com.suleymanuysal.moviesapp.databinding.TvEpisodesRowBinding;
import com.suleymanuysal.moviesapp.model.tv.Episodes;

import java.util.List;

public class TvEpisodesRvAdapter extends RecyclerView.Adapter<TvEpisodesRvAdapter.EpisodeViewHolder> {

    private List<Episodes> episodesList;
    private Context context;
    private TvEpisodesRowBinding binding;
    private int tvId;
    private int seasonNuber;


    public TvEpisodesRvAdapter(List<Episodes> episodesList, Context context, int tvId, int seasonNuber) {

        this.episodesList = episodesList;
        this.context = context;
        this.tvId = tvId;
        this.seasonNuber = seasonNuber;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TvEpisodesRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new EpisodeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        Episodes episode = episodesList.get(position);

        binding.textViewTvSeasonsName.setText((position + 1) + ". " + episode.getName());
        binding.textViewTvSeasonsOverview.setText(episode.getOverview());
        binding.textViewEpisodeRuntime.setText(String.valueOf("" + episode.getRuntime() + "m"));


        String imageUrl = episode.getStill_path();

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + imageUrl + "")
                .into(binding.tvSeasonImageView);


        binding.tvEpisodeCardView.setOnClickListener(v -> {

            Intent intent = new Intent(context, VideosActivity.class);

            intent.putExtra("new_info", "tv_episodes");
            intent.putExtra("tv_video_id", tvId);
            intent.putExtra("tv_season_number", episode.getSeason_number());
            intent.putExtra("tv_episode_number", episode.getEpisode_number());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return episodesList.size();
    }


    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TvEpisodesRowBinding binding;

        public EpisodeViewHolder(@NonNull TvEpisodesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
