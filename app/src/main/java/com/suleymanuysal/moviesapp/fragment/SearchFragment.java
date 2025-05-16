package com.suleymanuysal.moviesapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.suleymanuysal.moviesapp.adapter.SearchRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.FragmentSearchBinding;
import com.suleymanuysal.moviesapp.model.SearchModel;
import com.suleymanuysal.moviesapp.model.results.MovieResult;
import com.suleymanuysal.moviesapp.model.results.SearchResult;
import com.suleymanuysal.moviesapp.service.MovieAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieAPI movieAPI;
    private SearchRvAdapter searchRvAdapter;

    public static Fragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        movieAPI = ApiUtils.getMovieApi(Credentials.SEARCH_BASE_URL);

        binding.searchRV.setHasFixedSize(true);
        binding.searchRV.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));


        binding.searchView.clearFocus();


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                binding.progressBarSearch.setVisibility(View.VISIBLE);

                View view = getActivity().getCurrentFocus();
                closeKeybord(view);

                compositeDisposable.add(movieAPI.getAllMultiSearched(Credentials.API_KEY, query, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorComplete()
                                .doOnComplete(new Action() {
                                    @Override
                                    public void run() throws Throwable {

                                        binding.textViewTopResultsSearch.setVisibility(View.VISIBLE);

                                    }
                                })

                        .subscribe(this::searchedHandleResponse)
                );

                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                binding.textViewTopResultsSearch.setVisibility(View.GONE);
                binding.imageViewDataInfo.setVisibility(View.GONE);
                binding.searchRV.setVisibility(View.GONE);
                return true;
            }

            private void searchedHandleResponse(SearchResult searchResult) {

                if (searchResult.getSearchedList().size() > 0) {

                    binding.progressBarSearch.setVisibility(View.GONE);
                    binding.searchRV.setVisibility(View.VISIBLE);
                    binding.imageViewDataInfo.setVisibility(View.GONE);

                    searchRvAdapter = new SearchRvAdapter(searchResult.getSearchedList(),getContext());
                    binding.searchRV.setAdapter(searchRvAdapter);


                } else {
                    binding.progressBarSearch.setVisibility(View.GONE);
                    binding.imageViewDataInfo.setVisibility(View.VISIBLE);
                    binding.searchRV.setVisibility(View.GONE);


                }

            }
        });


        return binding.getRoot();
    }

   public void closeKeybord(View view){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
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