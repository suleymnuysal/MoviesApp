package com.suleymanuysal.moviesapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.activity.CastDetailActivity;
import com.suleymanuysal.moviesapp.activity.DetailActivity;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.databinding.SearchRowBinding;
import com.suleymanuysal.moviesapp.model.SearchModel;


import java.util.List;

public class SearchRvAdapter extends RecyclerView.Adapter<SearchRvAdapter.SearchViewHolder> {

    private List<SearchModel> searchList;
    private Context context;
    private SearchRowBinding binding;
    private HomeBottomSheetDesignBinding mbinding;
    private String name;
    private String releaseDate;
    private String posterPath;

    public SearchRvAdapter(List<SearchModel> searchList, Context context) {
        this.searchList = searchList;
        this.context = context;

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = SearchRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        SearchModel searchModel = searchList.get(position);


        if(searchModel.getMedia_type().equals("movie")){
          //  Toast.makeText(context, "Movie", Toast.LENGTH_SHORT).show();

            name = searchModel.getOriginal_title();
            releaseDate = searchModel.getRelease_date();
            posterPath = searchModel.getPoster_path();


        } else if (searchModel.getMedia_type().equals("tv")) {
            //Toast.makeText(context, "Tv", Toast.LENGTH_SHORT).show();

            name = searchModel.getOriginal_name();
            releaseDate = searchModel.getFirst_air_date();
            posterPath = searchModel.getPoster_path();


        }else if (searchModel.getMedia_type().equals("person")){
           // Toast.makeText(context, "Person", Toast.LENGTH_SHORT).show();
            name = searchModel.getOriginal_name();
            releaseDate = searchModel.getFirst_air_date();
            posterPath = searchModel.getProfile_path();
        }


        binding.SearchTitleName.setText(name);

        binding.serchRelaseDates.setText(releaseDate);

        String imageUrl = posterPath;

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + imageUrl + "")
                .into(binding.searchImageView);

        if(posterPath == null || name == null){
            binding.searchCardView.setVisibility(View.GONE);
        }

     /*   binding.searchOptionsmenu.setOnClickListener(v -> {
            ShowBottomSheet.showBottomSheet(searchModel, context, mbinding,"normal",v,0,"movieId","MyListMovies");

        });*/

        binding.searchCardView.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("info", "search");

            if(searchModel.getMedia_type().equals("movie")){

                intent.putExtra("tvOrMovie","movie");
                intent.putExtra("posterPath",searchModel.getPoster_path());
                intent.putExtra("searchModel",searchModel);

            } else if (searchModel.getMedia_type().equals("tv")) {


                intent.putExtra("tvOrMovie","tv");
                intent.putExtra("posterPath",searchModel.getPoster_path());
                intent.putExtra("searchModel",searchModel);

            } else if (searchModel.getMedia_type().equals("person")) {

                Intent intentForPerson = new Intent(context, CastDetailActivity.class);
                intentForPerson.putExtra("person_id",searchModel.getId());
                context.startActivity(intentForPerson);

            }

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        SearchRowBinding binding;

        public SearchViewHolder(@NonNull SearchRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
