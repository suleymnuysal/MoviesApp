package com.suleymanuysal.moviesapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.activity.DetailActivity;
import com.suleymanuysal.moviesapp.activity.VideosActivity;
import com.suleymanuysal.moviesapp.adapter.TvShowsCardViewRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.databinding.TvShowsFragmentBinding;
import com.suleymanuysal.moviesapp.model.results.TvResult;
import com.suleymanuysal.moviesapp.model.tv.TvGenres;
import com.suleymanuysal.moviesapp.service.MovieAPI;
import com.suleymanuysal.moviesapp.utils.DataFromAPI;
import com.suleymanuysal.moviesapp.utils.FireBaseHelper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentTvShows extends Fragment {

    private TvShowsFragmentBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieAPI movieAPI;
    private TvShowsCardViewRvAdapter tvShowsCardViewRvAdapter;
    private FirebaseFirestore firestore;
    private HomeBottomSheetDesignBinding mBinding;
    private FireBaseHelper fireBaseHelper;
    private String  id;
    private DataFromAPI dataFromAPI;

    public static Fragment newInstance() {
        return new FragmentTvShows();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TvShowsFragmentBinding.inflate(inflater, container, false);

        binding.shimmerForTvShows.startShimmer();

        setRvLayout(
                binding.popularTvRv,binding.onTheAirTvRv,binding.TopRatedTvRv,binding.airingTodayTvRv
                ,binding.trendingNowTvRv,binding.sciFiAndFantasyTvRv,binding.comediesTvRv,binding.soapTvRv,binding.actionAndAdventureTvRv
                ,binding.westernTvRv,binding.warPoliticsTvRv,binding.talkShowsTvRv,binding.realityTvRv,binding.mysteryTvRv
                ,binding.kidsTvRv,binding.familieTvRv,binding.dramaTvRv,binding.dokumentarfilmTvRv,binding.animationTvRv

        );


        movieAPI = ApiUtils.getMovieApi(Credentials.TV_BASE_URL);
        dataFromAPI = new DataFromAPI(compositeDisposable,getContext());

        firestore = FirebaseFirestore.getInstance();

        dataFromAPI.getTVDataFromAPI(movieAPI.getAllPopularTvs(Credentials.API_KEY, 2),binding.popularTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllOnTheAirTvs(Credentials.API_KEY, 3),binding.onTheAirTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTopRatedTvs(Credentials.API_KEY, 1),binding.TopRatedTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllAiringTodayTvs(Credentials.API_KEY, 1),binding.airingTodayTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10765"),binding.sciFiAndFantasyTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"35"),binding.comediesTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10766"),binding.soapTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10759"),binding.actionAndAdventureTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"37"),binding.westernTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10768"),binding.warPoliticsTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10767"),binding.talkShowsTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10764"),binding.realityTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"9648"),binding.mysteryTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10762"),binding.kidsTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"10751"),binding.familieTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"18"),binding.dramaTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"99"),binding.dokumentarfilmTvRv);
        dataFromAPI.getTVDataFromAPI(movieAPI.getAllTvCategories(Credentials.API_KEY,"16"),binding.animationTvRv);

        //dataFromAPI.getRecommendationMovies(movieAPI,null,binding,firestore,"MyListTvs","tvId");

        compositeDisposable.add(movieAPI.getAllTrendingTvsNow(Credentials.API_KEY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()

                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Throwable {
                                binding.shimmerForTvShows.stopShimmer();
                                binding.shimmerForTvShows.setVisibility(View.GONE);
                                binding.fragmentTvShowsContainer.setVisibility(View.VISIBLE);
                            }
                        })
                .subscribe(this::trendingHandleResponse)


        );


        return binding.getRoot();
    }

    private void setRvLayout(RecyclerView...rs){


        for (RecyclerView r:rs
        ) {
            r.setHasFixedSize(true);
            r.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        }


    }
    

    private void trendingHandleResponse(TvResult tvResult) {

        if (tvResult.getTvList().size() > 0) {

            
            ArrayList<String> genres = new ArrayList<>();
            for (int m :
                   tvResult.getTvList().get(0).getGenre_ids()) {

                TvGenres tvGenres = new TvGenres();
                genres.add(tvGenres.getGenresForTv(m));


            }

            binding.textViewGenres.setText(genres.toString().substring(1, (genres.toString().length() - 1)));

            String imageUrlr = tvResult.getTvList().get(0).getPoster_path();
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/original" + imageUrlr + "")
                    .into(binding.imageViewLatest);


            tvShowsCardViewRvAdapter = new TvShowsCardViewRvAdapter(tvResult.getTvList(), getContext());
            binding.trendingNowTvRv.setAdapter(tvShowsCardViewRvAdapter);


            binding.imageViewLatest.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("info", "tv");
                intent.putExtra("tv_id", tvResult.getTvList().get(0));
                startActivity(intent);

            });

            binding.tvShowsLatestInfo.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("info", "tv");
                intent.putExtra("tv_id", tvResult.getTvList().get(0));
                startActivity(intent);

            });

            fireBaseHelper = new FireBaseHelper(firestore,getContext(),mBinding,null,"tv", tvResult.getTvList().get(0),null,null,null);
            checkExisting(tvResult.getTvList().get(0).getId());

            binding.tvShowsLatestAddToMyList.setOnClickListener(v -> {

                fireBaseHelper.addToMyList(v);

                binding.tvShowsLatestAddToMyList.setVisibility(View.GONE);
                binding.tvShowsRemoveFromMyList.setVisibility(View.VISIBLE);

            });

            binding.tvShowsRemoveFromMyList.setOnClickListener(v -> {

                fireBaseHelper.removeFromMyList(v,id);

                binding.tvShowsLatestAddToMyList.setVisibility(View.VISIBLE);
                binding.tvShowsRemoveFromMyList.setVisibility(View.GONE);

            });

            binding.tvShowsPlayButon.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(), VideosActivity.class);
                intent.putExtra("new_info", "tvLatest");
                intent.putExtra("tv_video", tvResult.getTvList().get(0));
                startActivity(intent);

            });



        } else {
            Log.d("TvCardViewError", "No data from API");
        }
    }



    private void checkExisting(int tvId) {

        CollectionReference collectionReference = firestore.collection("MyListTvs");
        collectionReference.whereEqualTo("tvId", tvId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (!task.getResult().isEmpty()) {
                    //has movie
                    binding.tvShowsLatestAddToMyList.setVisibility(View.GONE);
                    binding.tvShowsRemoveFromMyList.setVisibility(View.VISIBLE);
                    for (DocumentSnapshot d : task.getResult().getDocuments()) {
                        id = d.getId();
                    }

                    binding.tvShowsRemoveFromMyList.setOnClickListener(v -> {

                        fireBaseHelper.removeFromMyList(v, id);
                        binding.tvShowsLatestAddToMyList.setVisibility(View.VISIBLE);
                        binding.tvShowsRemoveFromMyList.setVisibility(View.GONE);

                    });


                } else {
                    //has not movie
                    binding.tvShowsLatestAddToMyList.setVisibility(View.VISIBLE);
                    binding.tvShowsRemoveFromMyList.setVisibility(View.GONE);

                    binding.tvShowsLatestAddToMyList.setOnClickListener(v -> {

                        fireBaseHelper.addToMyList(v);
                        binding.tvShowsLatestAddToMyList.setVisibility(View.GONE);
                        binding.tvShowsRemoveFromMyList.setVisibility(View.VISIBLE);

                    });

                }

            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


}
