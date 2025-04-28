package com.suleymanuysal.moviesapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.adapter.TvEpisodesRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.ActivityDetailBinding;
import com.suleymanuysal.moviesapp.databinding.FragmentEpisodesRowBinding;
import com.suleymanuysal.moviesapp.model.tv.TvSeasonDetail;
import com.suleymanuysal.moviesapp.service.MovieAPI;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentEpisodes extends Fragment {

    public static ActivityDetailBinding mBinding;
    private FragmentEpisodesRowBinding binding;
    private MovieAPI movieAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int nTvId;
    private int seasonNumber;
    private int seasonNumberForApi;
    private TvEpisodesRvAdapter episodesRvAdapter;
    private String activity;

    public FragmentEpisodes(ActivityDetailBinding mBinding) {
        this.mBinding = mBinding;
    }

    public static FragmentEpisodes newInstance(int tvId, int seasonNumber, String activity) {

        FragmentEpisodes fragment = new FragmentEpisodes(mBinding);
        Bundle bundle = new Bundle();
        bundle.putInt("tv_id", tvId);
        bundle.putInt("season_number", seasonNumber);
        bundle.putString("activity_info", activity);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            nTvId = getArguments().getInt("tv_id");
            seasonNumber = getArguments().getInt("season_number");
            activity = getArguments().getString("activity_info");
        } else {
            Toast.makeText(getContext(), "No id from detail activity ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEpisodesRowBinding.inflate(inflater, container, false);

        binding.recyclerViewEpisodes.setHasFixedSize(true);
        binding.recyclerViewEpisodes.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));


        movieAPI = ApiUtils.getMovieApi(Credentials.TV_BASE_URL);

        mBinding.detailTabLayout.getTabAt(0).setText("Episodes");
        mBinding.detailTabLayout.getTabAt(1).setText("Related");
        mBinding.detailTabLayout.getTabAt(2).setText("More Details");

        ArrayList<String> seasonStringArrayList = new ArrayList<>();

        for (int i = 1; i <= seasonNumber; i++) {
            seasonStringArrayList.add("Season " + i);
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, seasonStringArrayList);
        binding.spinnerTvSeasons.setAdapter(stringArrayAdapter);
        binding.spinnerTvSeasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seasonNumberForApi = position + 1;
                getData(seasonNumberForApi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }


    public void getData(int seasonNumberForApi) {


        compositeDisposable.add(movieAPI.getTvSeasonEpisodes(nTvId, seasonNumberForApi, Credentials.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(this::episodesHandleResponse)
        );


    }

    private void episodesHandleResponse(TvSeasonDetail tvSeasonDetail) {
        if (tvSeasonDetail.getEpisodes().size() > 0) {
            binding.progressBarEpisodes.setVisibility(View.GONE);

            episodesRvAdapter = new TvEpisodesRvAdapter(tvSeasonDetail.getEpisodes(), getContext(), nTvId, seasonNumber);

            binding.recyclerViewEpisodes.setAdapter(episodesRvAdapter);


        } else {
            Toast.makeText(getContext(), "No Episode Found", Toast.LENGTH_SHORT).show();
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