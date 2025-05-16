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
import com.suleymanuysal.moviesapp.adapter.CastAndCrewRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.ActivityDetailBinding;
import com.suleymanuysal.moviesapp.databinding.FragmentMoreDetailsForMovieBinding;
import com.suleymanuysal.moviesapp.model.results.MovieCastAndCrewResult;
import com.suleymanuysal.moviesapp.service.MovieAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MoreDetailsForMovieFragment extends Fragment {

    public static ActivityDetailBinding mbinding;
    private FragmentMoreDetailsForMovieBinding binding;
    private MovieAPI movieAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CastAndCrewRvAdapter castAndCrewRvAdapter;
    private int nMovieId;
    private int nTvid;
    private String activity;
    private String movieOrTv;
    private int lastMovieId;
    private int lastTvId;

    public MoreDetailsForMovieFragment(ActivityDetailBinding mbinding) {
        this.mbinding = mbinding;
    }

    public static MoreDetailsForMovieFragment newInstance(int movieID, int tvId, String activity,String movieOrTv) {

        MoreDetailsForMovieFragment fragment = new MoreDetailsForMovieFragment(mbinding);
        Bundle bundle = new Bundle();
        bundle.putInt("movie_id", movieID);
        bundle.putInt("tv_id", tvId);
        bundle.putString("activity_info", activity);
        bundle.putString("movie_or_tv", movieOrTv);
        fragment.setArguments(bundle);

        return fragment;
    }

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
        binding = FragmentMoreDetailsForMovieBinding.inflate(inflater, container, false);

        binding.recyclerViewCastAndCrew.setHasFixedSize(true);
        binding.recyclerViewCastAndCrew.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));


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

        if(lastMovieId != 0){
            compositeDisposable.add(movieAPI.getCastAndCrew(lastMovieId, Credentials.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorComplete()
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Throwable {
                                    binding.progressBarCastAndCrew.setVisibility(View.GONE);
                                }
                            })
                    .subscribe(this::castAndCrewHandleResponse)
            );
        }

        if(lastTvId != 0){
            compositeDisposable.add(movieAPI.getCastAndCrewForTv(lastTvId, Credentials.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorComplete()
                            .doOnComplete(new Action() {
                                @Override
                                public void run() throws Throwable {
                                    binding.progressBarCastAndCrew.setVisibility(View.GONE);
                                }
                            })
                    .subscribe(this::castAndCrewTvHandleResponse)
            );
        }



        return binding.getRoot();
    }

    private void castAndCrewTvHandleResponse(MovieCastAndCrewResult movieCastAndCrewResult) {

        if (movieCastAndCrewResult.getGetCasts().size() > 0) {

            castAndCrewRvAdapter = new CastAndCrewRvAdapter(getContext(), movieCastAndCrewResult.getGetCasts(), movieCastAndCrewResult.getGetCrews(), binding);
            binding.recyclerViewCastAndCrew.setAdapter(castAndCrewRvAdapter);


        } else {

            binding.textView9.setVisibility(View.GONE);
            binding.imageViewNoDataBg.setVisibility(View.VISIBLE);
            binding.textViewDataFound.setVisibility(View.VISIBLE);
            binding.textViewDataFound.setText("Nothing to show");
        }
    }


    private void castAndCrewHandleResponse(MovieCastAndCrewResult movieCastAndCrewResult) {

        if (movieCastAndCrewResult.getGetCasts().size() > 0) {

            castAndCrewRvAdapter = new CastAndCrewRvAdapter(getContext(), movieCastAndCrewResult.getGetCasts(), movieCastAndCrewResult.getGetCrews(), binding);
            binding.recyclerViewCastAndCrew.setAdapter(castAndCrewRvAdapter);

        } else {
            binding.textView9.setVisibility(View.GONE);
            binding.imageViewNoDataBg.setVisibility(View.VISIBLE);
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