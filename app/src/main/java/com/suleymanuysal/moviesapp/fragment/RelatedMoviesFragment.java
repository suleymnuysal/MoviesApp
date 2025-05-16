package com.suleymanuysal.moviesapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.adapter.SimilarRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.ActivityDetailBinding;
import com.suleymanuysal.moviesapp.databinding.FragmentRelatedMoviesBinding;
import com.suleymanuysal.moviesapp.model.results.MovieResult;
import com.suleymanuysal.moviesapp.model.results.TvResult;
import com.suleymanuysal.moviesapp.service.MovieAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RelatedMoviesFragment extends Fragment {

    public static ActivityDetailBinding mbinding;
    private FragmentRelatedMoviesBinding binding;
    private MovieAPI movieAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SimilarRvAdapter similarRvAdapter;
    private int nMovieId;
    private int nTvid;
    private String activity;
    private int lastMovieId;
    private int lastTvId;
    private String movieOrTv;


    public RelatedMoviesFragment(ActivityDetailBinding mbinding) {
        this.mbinding = mbinding;
    }


    public static RelatedMoviesFragment newInstance(int movieID, int tvId, String activity,String movieOrTv) {

        RelatedMoviesFragment fragment = new RelatedMoviesFragment(mbinding);
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieID);
        bundle.putInt("tv_id", tvId);
        bundle.putString("activity_info", activity);
        bundle.putString("movie_or_tv", movieOrTv);
        fragment.setArguments(bundle);


        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nMovieId = getArguments().getInt("movie_id");
            nTvid = getArguments().getInt("tv_id");
            activity = getArguments().getString("activity_info");
            movieOrTv = getArguments().getString("movie_or_tv");
        } else {
            Toast.makeText(getContext(), "No id from detail activity ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRelatedMoviesBinding.inflate(inflater, container, false);

        binding.recyclerViewSimalar.setHasFixedSize(true);
        binding.recyclerViewSimalar.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        movieAPI = ApiUtils.getMovieApi(Credentials.MOVIE_BASE_URL);


        switch (activity) {
            case "normal":
                lastMovieId = nMovieId;

                break;
            case "tv":
                lastTvId = nTvid;

                break;
            case "myList":

                if (movieOrTv.equals("movie")) {
                    lastMovieId = nMovieId;

                } else if (movieOrTv.equals("mtv")) {
                    lastTvId = nTvid;

                }
                break;
            case "search":

                if (movieOrTv.equals("movie")) {
                    lastMovieId = nMovieId;

                } else if (movieOrTv.equals("mtv")) {
                    lastTvId = nTvid;

                }
                break;
        }

        if(lastMovieId!= 0){
            compositeDisposable.add(movieAPI.getSimilarMovies(lastMovieId, Credentials.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorComplete()
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Throwable {
                            binding.progressBarRelated.setVisibility(View.GONE);
                        }
                    })
                    .subscribe(this::similarHandleResponse)
            );
        }

        if(lastTvId !=0){
            compositeDisposable.add(movieAPI.getAllSimilarTvs(lastTvId, Credentials.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorComplete()
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Throwable {
                                    binding.progressBarRelated.setVisibility(View.GONE);
                                }
                            })
                    .subscribe(this::similarTvHandleResponse)
            );
        }

        return binding.getRoot();
    }

    private void similarTvHandleResponse(TvResult tvResult) {

        if (tvResult.getTvList().size() > 0) {

            similarRvAdapter = new SimilarRvAdapter(getContext(), null,tvResult.getTvList());
            binding.recyclerViewSimalar.setAdapter(similarRvAdapter);

        } else {
            binding.imageViewReletedNoDataBg.setVisibility(View.VISIBLE);
            binding.textViewDataFound.setVisibility(View.VISIBLE);
            binding.textViewDataFound.setText("Nothing to show");



        }
    }

    private void similarHandleResponse(MovieResult movieResult) {
        if (movieResult.getMovieList().size() > 0) {

            similarRvAdapter = new SimilarRvAdapter(getContext(), movieResult.getMovieList(),null);
            binding.recyclerViewSimalar.setAdapter(similarRvAdapter);


        } else {
            binding.imageViewReletedNoDataBg.setVisibility(View.VISIBLE);
            binding.textViewDataFound.setVisibility(View.VISIBLE);
            binding.textViewDataFound.setText("Nothing to show");
            mbinding.viewPagerDetail.setCurrentItem(R.id.containerForCast);

        }
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