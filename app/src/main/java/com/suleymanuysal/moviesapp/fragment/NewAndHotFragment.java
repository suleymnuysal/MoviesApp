package com.suleymanuysal.moviesapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.suleymanuysal.moviesapp.adapter.NewAndHotUpcomingRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.NewAndHotFragmentBinding;
import com.suleymanuysal.moviesapp.model.results.MovieResult;
import com.suleymanuysal.moviesapp.model.results.TvResult;
import com.suleymanuysal.moviesapp.service.MovieAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewAndHotFragment extends Fragment {

    private NewAndHotFragmentBinding binding;
    private MovieAPI movieAPI;
    private MovieAPI tvApi;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NewAndHotUpcomingRvAdapter upcomingRvAdapter;

    public static Fragment newInstance() {
        return new NewAndHotFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = NewAndHotFragmentBinding.inflate(inflater, container, false);
        binding.shimmerForNewAndHot.startShimmer();

        movieAPI = ApiUtils.getMovieApi(Credentials.MOVIE_BASE_URL);
        tvApi = ApiUtils.getMovieApi(Credentials.TV_BASE_URL);

        binding.recyclerViewComingSoon.setHasFixedSize(true);
        binding.recyclerViewComingSoon.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        binding.recyclerViewComingTvs.setHasFixedSize(true);
        binding.recyclerViewComingTvs.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        compositeDisposable.add(movieAPI.getAllUpcomingMovies(Credentials.API_KEY, 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(this::upcomingHandleResponse)


        );

        compositeDisposable.add(tvApi.getAllAiringTodayTvs(Credentials.API_KEY, 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                        .doOnComplete(new Action() {
                            @Override
                            public void run() throws Throwable {

                                binding.shimmerForNewAndHot.stopShimmer();
                                binding.shimmerForNewAndHot.setVisibility(View.GONE);
                                binding.newAndHotNested.setVisibility(View.VISIBLE);

                            }
                        })
                .subscribe(this::airingTodayHandleResponse)


        );


        return binding.getRoot();
    }

    private void airingTodayHandleResponse(TvResult tvResult) {

        if(tvResult.getTvList().size()>0){

            upcomingRvAdapter = new NewAndHotUpcomingRvAdapter(null,tvResult.getTvList(),getContext());
            binding.recyclerViewComingTvs.setAdapter(upcomingRvAdapter);


        }else {
            Toast.makeText(getContext(), "No data from API", Toast.LENGTH_SHORT).show();
        }
    }

    private void upcomingHandleResponse(MovieResult upcomingMovieResult) {
        if (upcomingMovieResult.getMovieList().size() > 0) {


            upcomingRvAdapter = new NewAndHotUpcomingRvAdapter(upcomingMovieResult.getMovieList(),null, getContext());
            binding.recyclerViewComingSoon.setAdapter(upcomingRvAdapter);

        } else {
            Toast.makeText(getContext(), "No data from API", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        binding = null;
    }
}
