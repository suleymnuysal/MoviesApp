package com.suleymanuysal.moviesapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.activity.DetailActivity;
import com.suleymanuysal.moviesapp.activity.VideosActivity;
import com.suleymanuysal.moviesapp.adapter.HomeCardViewRvAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.databinding.HomeFragmentBinding;
import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.model.results.MovieResult;
import com.suleymanuysal.moviesapp.service.MovieAPI;
import com.suleymanuysal.moviesapp.utils.DataFromAPI;
import com.suleymanuysal.moviesapp.utils.FireBaseHelper;
import com.suleymanuysal.moviesapp.utils.ShowBottomSheet;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FragmentHome extends Fragment {

    private HomeFragmentBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private HomeCardViewRvAdapter homeCardViewRvAdapter;
    private MovieAPI movieAPI;
    private FireBaseHelper fireBaseHelper;
    private FirebaseFirestore firestore;
    private DataFromAPI dataFromAPI;
    private HomeBottomSheetDesignBinding mbinding;
    private String  id;


    // TODO: 5.07.2023 add an attribute that saves caches when user surfs between fragments


    public static Fragment newInstance() {
        return new FragmentHome();
    }
    

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(inflater, container, false);

        setRvLayout(
                 binding.recyclerViewNowPlaying,binding.recyclerViewtrendingNow,binding.recyclerViewPopular
                ,binding.recyclerViewUpcoming,binding.recyclerViewTopRated
                ,binding.recyclerViewAction,binding.recyclerViewScienceFiction,binding.recyclerViewAdvanture
                ,binding.recyclerViewAnimation,binding.recyclerViewComedy,binding.recyclerViewThriller
                ,binding.recyclerViewDokumentarfilm,binding.recyclerViewDrama,binding.recyclerViewFamilie
                ,binding.recyclerViewFantasy,binding.recyclerViewHistorie,binding.recyclerViewHorror
                ,binding.recyclerViewMystery,binding.recyclerViewLove,binding.recyclerViewTVFilm
                ,binding.recyclerViewWar,binding.recyclerViewWestern

        );

        binding.shimmerForMyListHome.startShimmer();

        movieAPI = ApiUtils.getMovieApi(Credentials.MOVIE_BASE_URL);
        dataFromAPI = new DataFromAPI(compositeDisposable,getContext());

        firestore = FirebaseFirestore.getInstance();

        fireBaseHelper = new FireBaseHelper(firestore,getContext(),null,null,null,null,null,null,null);

        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllNowPlayingMovies(Credentials.API_KEY, 1),binding.recyclerViewNowPlaying);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllUpcomingMovies(Credentials.API_KEY, 2),binding.recyclerViewUpcoming);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllTopRatedMovies(Credentials.API_KEY, 1),binding.recyclerViewTopRated);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllTrendingMoviesNow(Credentials.API_KEY, 1),binding.recyclerViewtrendingNow);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "12"),binding.recyclerViewAdvanture);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "99"),binding.recyclerViewDokumentarfilm);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "28"),binding.recyclerViewAction);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "16"),binding.recyclerViewAnimation);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "35"),binding.recyclerViewComedy);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "80"),binding.recyclerViewThriller);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "18"),binding.recyclerViewDrama);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "10751"),binding.recyclerViewFamilie);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "36"),binding.recyclerViewHistorie);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "27"),binding.recyclerViewHorror);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "9648"),binding.recyclerViewMystery);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "10749"),binding.recyclerViewLove);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "878"),binding.recyclerViewScienceFiction);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "10770"),binding.recyclerViewTVFilm);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "10752"),binding.recyclerViewWar);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "37"),binding.recyclerViewWestern);
        dataFromAPI.getMovieDataFromAPI(movieAPI.getAllMovieCategories(Credentials.API_KEY, "14"),binding.recyclerViewFantasy);


        compositeDisposable.add(movieAPI.getAllPopularMovies(Credentials.API_KEY, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Throwable {

                        binding.shimmerForMyListHome.stopShimmer();
                        binding.shimmerForMyListHome.setVisibility(View.GONE);
                        binding.homeFragmentContainer.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(this::popularHandleResponse)
        );
        return binding.getRoot();
    }

    private void setRvLayout(RecyclerView ...rs){

        for (RecyclerView r:rs
        ) {
            r.setHasFixedSize(true);
            r.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        }

    }

    private void popularHandleResponse(MovieResult movieResult) {

        if (!movieResult.getMovieList().isEmpty()) {

            homeCardViewRvAdapter = new HomeCardViewRvAdapter(getContext(), movieResult.getMovieList());
            binding.recyclerViewPopular.setAdapter(homeCardViewRvAdapter);

            Movie randomMovie = movieResult.getMovieList().get(4);
            String randomImage = randomMovie.getPoster_path();
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/w500"+randomImage+"")
                    .into(binding.imageViewHomeLatest);

            binding.imageViewHomeLatest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("info", "normal");
                    intent.putExtra("MovieId", randomMovie);
                    getContext().startActivity(intent);
                }
            });

            binding.movieLatestInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("info", "normal");
                    intent.putExtra("MovieId", randomMovie);
                    getContext().startActivity(intent);
                }
            });

            fireBaseHelper = new FireBaseHelper(firestore,getContext(),mbinding,randomMovie,"normal", null,null,null,null);
            checkExisting(randomMovie.getId());

            binding.movieLatestAddToMyList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fireBaseHelper.addToMyList(view);

                    binding.movieLatestAddToMyList.setVisibility(View.GONE);
                    binding.movieLatestRemoveFromMyList.setVisibility(View.VISIBLE);
                }
            });

            binding.movieLatestRemoveFromMyList.setOnClickListener(v -> {

                fireBaseHelper.removeFromMyList(v,id);

                binding.movieLatestAddToMyList.setVisibility(View.VISIBLE);
                binding.movieLatestRemoveFromMyList.setVisibility(View.GONE);

            });

            binding.movielatestPlayButon.setOnClickListener(v -> {

                Intent intent = new Intent(getContext(), VideosActivity.class);
                intent.putExtra("new_info", "movie");
                intent.putExtra("movie_video", randomMovie);
                startActivity(intent);

            });

        } else {
            Log.d("MovieCardViewError", "No data from API");
        }

    }

    private void checkExisting(int movieId) {

        CollectionReference collectionReference = firestore.collection("MyListMovies");
        collectionReference.whereEqualTo("movieId", movieId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (!task.getResult().isEmpty()) {
                    //has movie
                    binding.movieLatestAddToMyList.setVisibility(View.GONE);
                    binding.movieLatestRemoveFromMyList.setVisibility(View.VISIBLE);
                    for (DocumentSnapshot d : task.getResult().getDocuments()) {
                        id = d.getId();
                    }

                    binding.movieLatestRemoveFromMyList.setOnClickListener(v -> {

                        fireBaseHelper.removeFromMyList(v, id);
                        binding.movieLatestAddToMyList.setVisibility(View.VISIBLE);
                        binding.movieLatestRemoveFromMyList.setVisibility(View.GONE);

                    });


                } else {
                    //has not movie
                    binding.movieLatestAddToMyList.setVisibility(View.VISIBLE);
                    binding.movieLatestRemoveFromMyList.setVisibility(View.GONE);

                    binding.movieLatestAddToMyList.setOnClickListener(v -> {

                        fireBaseHelper.addToMyList(v);
                        binding.movieLatestAddToMyList.setVisibility(View.GONE);
                        binding.movieLatestRemoveFromMyList.setVisibility(View.VISIBLE);

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
