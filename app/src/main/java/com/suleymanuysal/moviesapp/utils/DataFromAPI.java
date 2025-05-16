package com.suleymanuysal.moviesapp.utils;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.suleymanuysal.moviesapp.adapter.HomeCardViewRvAdapter;
import com.suleymanuysal.moviesapp.adapter.TvShowsCardViewRvAdapter;
import com.suleymanuysal.moviesapp.model.results.MovieResult;
import com.suleymanuysal.moviesapp.model.results.TvResult;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DataFromAPI {

    private CompositeDisposable compositeDisposable;
    private HomeCardViewRvAdapter homeCardViewRvAdapter;
    private TvShowsCardViewRvAdapter tvShowsCardViewRvAdapter;
    private Context context;


    public DataFromAPI(CompositeDisposable compositeDisposable, Context context) {
        this.compositeDisposable = compositeDisposable;
        this.context = context;

    }

    public void getMovieDataFromAPI(Observable<MovieResult> observable, RecyclerView r){

        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(movieResult -> {

                    homeCardViewRvAdapter = new HomeCardViewRvAdapter(context,movieResult.getMovieList());
                    r.setAdapter(homeCardViewRvAdapter);

                })
        );


    }

    public void getTVDataFromAPI(Observable<TvResult> observable, RecyclerView r){

        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(tvResult -> {

                    tvShowsCardViewRvAdapter = new TvShowsCardViewRvAdapter(tvResult.getTvList(),context);
                    r.setAdapter(tvShowsCardViewRvAdapter);

                })
        );


    }

}
