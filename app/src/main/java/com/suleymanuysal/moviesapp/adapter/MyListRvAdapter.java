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
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.databinding.MyListRowBinding;
import com.suleymanuysal.moviesapp.model.movie.MyListMovieModel;
import com.suleymanuysal.moviesapp.utils.ShowBottomSheet;

import java.util.ArrayList;

public class MyListRvAdapter extends RecyclerView.Adapter<MyListRvAdapter.FavWidgetsHolder> {

    private Context context;
    private MyListRowBinding binding;
    private ArrayList<MyListMovieModel> movieDataList;
    private HomeBottomSheetDesignBinding mbinding;



    public MyListRvAdapter(Context context, ArrayList<MyListMovieModel> movieDataList) {
        this.context = context;
        this.movieDataList =movieDataList;

    }

    @NonNull
    @Override
    public FavWidgetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = MyListRowBinding.inflate(LayoutInflater.from(parent.getContext()));


        return new FavWidgetsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavWidgetsHolder holder, int position) {
        holder.setIsRecyclable(false);

        MyListMovieModel movie = movieDataList.get(position);

        binding.textViewMyListName.setText(movie.getName());
        binding.textViewMyListRelaseDate.setText(movie.getReleaseDate());

        Float f = new Float((movie.getVoteAverage()*5)/10);

        binding.ratingBarMyList.setRating(f);

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" +movie.getPosterPath() + "")
                .placeholder(R.drawable.movie_place_holder)
                .into(binding.imageViewMyList);


        binding.myListCardView.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("info", "myList");

            if(movie.getMovieOrTv().equals("movie")){

                intent.putExtra("tvOrMovie","movie");
                intent.putExtra("myListMovieId",movie.getMovieId());
                intent.putExtra("posterPath",movie.getPosterPath());
                intent.putExtra("myListMovieModel",movie);

            } else if (movie.getMovieOrTv().equals("tv")) {

                intent.putExtra("tvOrMovie","tv");
                intent.putExtra("myListMovieId",movie.getMovieId());
                intent.putExtra("posterPath",movie.getPosterPath());
                intent.putExtra("myListMovieModel",movie);

            }

            context.startActivity(intent);
        });

        binding.imageViewMyListOptionsMenu.setOnClickListener(v -> {

            if(movie.getMovieOrTv().equals("movie")){

                ShowBottomSheet.showBottomSheet(null,context,mbinding,"normal",v,(int) movie.getMovieId(),"movieId","MyListMovies");

            } else if (movie.getMovieOrTv().equals("tv")) {

                ShowBottomSheet.showBottomSheet(null,context,mbinding,"tv",v,(int) movie.getMovieId(),"tvId","MyListTvs");


            }



        });


    }

    @Override
    public int getItemCount() {
        return movieDataList.size();
    }


    public class FavWidgetsHolder extends RecyclerView.ViewHolder {
        MyListRowBinding binding;

        public FavWidgetsHolder(@NonNull MyListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
