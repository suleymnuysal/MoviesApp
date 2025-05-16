package com.suleymanuysal.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.activity.CastDetailActivity;
import com.suleymanuysal.moviesapp.databinding.CastAndCrewRowBinding;
import com.suleymanuysal.moviesapp.databinding.FragmentMoreDetailsForMovieBinding;
import com.suleymanuysal.moviesapp.model.movie.MovieCastAndCrew;

import java.util.ArrayList;
import java.util.List;

public class CastAndCrewRvAdapter extends RecyclerView.Adapter<CastAndCrewRvAdapter.CastAndCrewWidgetsHolder> {

    private Context context;
    private List<MovieCastAndCrew> casts;
    private List<MovieCastAndCrew> crews;
    private CastAndCrewRowBinding binding;
    private FragmentMoreDetailsForMovieBinding mBinding;
    private ArrayList<MovieCastAndCrew> directors = new ArrayList<>();


    public CastAndCrewRvAdapter(Context context, List<MovieCastAndCrew> casts, List<MovieCastAndCrew> crews, FragmentMoreDetailsForMovieBinding mBinding) {
        this.context = context;
        this.casts = casts;
        this.mBinding = mBinding;
        this.crews = crews;
    }


    @NonNull
    @Override
    public CastAndCrewWidgetsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CastAndCrewRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new CastAndCrewWidgetsHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull CastAndCrewWidgetsHolder holder, int position) {
        holder.setIsRecyclable(false);

        MovieCastAndCrew cast = casts.get(position);

        for (MovieCastAndCrew j : crews
        ) {
            if (j.getJob().equals("Director")||j.getJob().equals("Executive Producer")) {
                directors.add(j);

            }

        }

        String imageUrl = cast.getProfile_path();

        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + imageUrl + "")
                .placeholder(R.drawable.place_holder_tv)
                .into(binding.castAndCrewImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(R.drawable.place_holder_tv).into(mBinding.castAndCrewImageViewDirector);
                    }
                });


        binding.textViewCastName.setText(cast.getName());

        binding.castAndCrewCardView.setOnClickListener(v -> {

            Intent intent = new Intent(context, CastDetailActivity.class);
            intent.putExtra("person_id",cast.getId());
            context.startActivity(intent);

        });

        mBinding.castAndCrewImageViewDirector.setOnClickListener(v -> {

            Intent intent = new Intent(context, CastDetailActivity.class);
            intent.putExtra("person_id", directors.get(position).getId());
            context.startActivity(intent);

        });


        if (directors.size() > 0) {

            String imageUrlDirector = directors.get(position).getProfile_path();

            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500" + imageUrlDirector + "")
                    .placeholder(R.drawable.place_holder_tv)
                    .into(mBinding.castAndCrewImageViewDirector, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(R.drawable.place_holder_tv).into(mBinding.castAndCrewImageViewDirector);

                        }
                    });

            String directorDetail = "Department: " + directors.get(position).getDepartment() + "\nPopularity:  " + directors.get(position).getPopularity();

            mBinding.textViewDirectorDetail.setText(directorDetail);
            mBinding.textViewDirectorName.setText(directors.get(position).getName());

        } else {
            Picasso.get().load(R.drawable.place_holder_tv).into(mBinding.castAndCrewImageViewDirector);
        }

    }

    @Override
    public int getItemCount() {
        if (casts.size() >= 6) {
            return 6;
        } else {
            return casts.size();
        }

    }

    public class CastAndCrewWidgetsHolder extends RecyclerView.ViewHolder {
        CastAndCrewRowBinding binding;

        public CastAndCrewWidgetsHolder(@NonNull CastAndCrewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }

}
