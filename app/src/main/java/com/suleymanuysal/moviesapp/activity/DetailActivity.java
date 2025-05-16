package com.suleymanuysal.moviesapp.activity;

import static com.suleymanuysal.moviesapp.R.color.powder_blue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.adapter.ViewPagerDetailAdapter;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.ActivityDetailBinding;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.fragment.FragmentEpisodes;
import com.suleymanuysal.moviesapp.fragment.MoreDetailsForMovieFragment;
import com.suleymanuysal.moviesapp.fragment.RelatedMoviesFragment;
import com.suleymanuysal.moviesapp.model.SearchModel;
import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.model.movie.MovieDetail;
import com.suleymanuysal.moviesapp.model.movie.MovieGenres;
import com.suleymanuysal.moviesapp.model.movie.MyListMovieModel;
import com.suleymanuysal.moviesapp.model.tv.Seasons;
import com.suleymanuysal.moviesapp.model.tv.Tv;
import com.suleymanuysal.moviesapp.model.tv.TvDetail;
import com.suleymanuysal.moviesapp.service.MovieAPI;
import com.suleymanuysal.moviesapp.utils.FireBaseHelper;


import java.text.DecimalFormat;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private HomeBottomSheetDesignBinding homeBinding;
    private int incomeMovieId;
    private String incomePosterPatch;
    private MovieAPI movieAPI;
    private String incomeInfo;
    private String tvOrMovieInfo;
    private Movie movie;
    private Tv tv;
    private int incomeTvId;
    private int seasonNumber;
    private int myListMovieId;
    private int myListTvId;
    private String id = null;
    private FirebaseFirestore firestore;
    private FireBaseHelper fireBaseHelper;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String collectionNameForMovies = "MyListMovies";
    private static final String collectionNameForTvs = "MyListTvs";
    private static final String fieldPathForMovies = "movieId";
    private static final String fieldPathForForTvs = "tvId";
    private String collectionPath;
    private String fieldPath;
    private int fieldId;
    private String movieOrTvMyList = null;
    private boolean isInMyListSection;
    private MyListMovieModel myListMovieModel;
    private SearchModel searchModel;
    private boolean isAdded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        binding.toolbarDetail.setTitle("Detail");
        binding.toolbarDetail.setTitleTextColor(getColor(powder_blue));
        setSupportActionBar(binding.toolbarDetail);

        binding.detailTabLayout.addTab(binding.detailTabLayout.newTab().setText("Related"));
        binding.detailTabLayout.addTab(binding.detailTabLayout.newTab().setText("More Details"));
        binding.detailTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        new RelatedMoviesFragment(binding);
        new MoreDetailsForMovieFragment(binding);
        new FragmentEpisodes(binding);

        movieAPI = ApiUtils.getMovieApi(Credentials.MOVIE_BASE_URL);

        binding.toolbarDetail.setNavigationOnClickListener(v -> {

            startActivity(new Intent(DetailActivity.this, MainActivity.class));
            finish();

        });

        binding.detailTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPagerDetail.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.viewPagerDetail.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.detailTabLayout.getTabAt(position).select();
            }
        });


        incomeInfo = getIntent().getStringExtra("info");


        switch (incomeInfo) {
            case "normal":

                movie = (Movie) getIntent().getSerializableExtra("MovieId");
                incomeMovieId = movie.getId();
                incomePosterPatch = movie.getPoster_path();
                isInMyListSection = false;

                collectionPath = collectionNameForMovies;
                fieldPath = fieldPathForMovies;
                fieldId = movie.getId();

                compositeDisposable.add(movieAPI.getMovieDetail(incomeMovieId, Credentials.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorComplete()
                        .subscribe(DetailActivity.this::movieDetailHandleResponse)

                );


                break;
            case "tv":

                tv = (Tv) getIntent().getSerializableExtra("tv_id");
                incomeTvId = tv.getId();
                incomePosterPatch = tv.getPoster_path();
                binding.detailTabLayout.addTab(binding.detailTabLayout.newTab().setText("Episodes"));
                isInMyListSection = false;

                collectionPath = collectionNameForTvs;
                fieldPath = fieldPathForForTvs;
                fieldId = tv.getId();

                compositeDisposable.add(movieAPI.getTvDetail(incomeTvId, Credentials.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorComplete()
                        .subscribe(DetailActivity.this::tvDetailHandleResponse)
                );


                break;
            case "myList":

                tvOrMovieInfo = getIntent().getStringExtra("tvOrMovie");

                if (tvOrMovieInfo.equals("movie")) {

                    myListMovieModel = (MyListMovieModel) getIntent().getSerializableExtra("myListMovieModel");
                    myListMovieId = (int) getIntent().getLongExtra("myListMovieId", 0);
                    incomeMovieId = myListMovieId;
                    incomePosterPatch = getIntent().getStringExtra("posterPath");
                    isInMyListSection = true;
                    movieOrTvMyList = "movie";

                    //for checking the situation of existing for the movie
                    collectionPath = collectionNameForMovies;
                    fieldPath = fieldPathForMovies;
                    fieldId = myListMovieId;


                    compositeDisposable.add(movieAPI.getMovieDetail(myListMovieId, Credentials.API_KEY)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorComplete()
                            .subscribe(DetailActivity.this::movieDetailHandleResponse)
                    );


                } else if (tvOrMovieInfo.equals("tv")) {

                    myListMovieModel = (MyListMovieModel) getIntent().getSerializableExtra("myListMovieModel");
                    myListTvId = (int) getIntent().getLongExtra("myListMovieId", 0);
                    incomeTvId = myListTvId;
                    incomePosterPatch = getIntent().getStringExtra("posterPath");
                    binding.detailTabLayout.addTab(binding.detailTabLayout.newTab().setText("Episodes"));
                    isInMyListSection = true;
                    movieOrTvMyList = "tv";

                    //for checking the situation of existing for tv
                    collectionPath = collectionNameForTvs;
                    fieldPath = fieldPathForForTvs;
                    fieldId = myListTvId;

                    compositeDisposable.add(movieAPI.getTvDetail(myListTvId, Credentials.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorComplete()
                            .subscribe(DetailActivity.this::tvDetailHandleResponse)
                    );
                }


                break;
            case "search":

                tvOrMovieInfo = getIntent().getStringExtra("tvOrMovie");

                if (tvOrMovieInfo.equals("movie")) {

                    searchModel = (SearchModel) getIntent().getSerializableExtra("searchModel");
                    incomeMovieId = searchModel.getId();
                    incomePosterPatch = getIntent().getStringExtra("posterPath");
                    isInMyListSection = false;
                    movieOrTvMyList = "movie";

                    //for checking the situation of existing for movie
                    collectionPath = collectionNameForMovies;
                    fieldPath = fieldPathForMovies;
                    fieldId = incomeMovieId;


                    compositeDisposable.add(movieAPI.getMovieDetail(incomeMovieId, Credentials.API_KEY)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorComplete()
                            .subscribe(DetailActivity.this::movieDetailHandleResponse)
                    );


                } else if (tvOrMovieInfo.equals("tv")) {

                    searchModel = (SearchModel) getIntent().getSerializableExtra("searchModel");
                    incomeTvId = searchModel.getId();
                    incomePosterPatch = getIntent().getStringExtra("posterPath");
                    binding.detailTabLayout.addTab(binding.detailTabLayout.newTab().setText("Episodes"));
                    isInMyListSection = false;
                    movieOrTvMyList = "tv";

                    //for checking the situation of existing for tv
                    collectionPath = collectionNameForTvs;
                    fieldPath = fieldPathForForTvs;
                    fieldId = incomeTvId;

                    compositeDisposable.add(movieAPI.getTvDetail(incomeTvId, Credentials.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorComplete()
                            .subscribe(DetailActivity.this::tvDetailHandleResponse)
                    );

                }
                break;
        }


        checkExistingForDetail(collectionPath,fieldPath,fieldId);

        fireBaseHelper = new FireBaseHelper(firestore,this,null,movie
                ,incomeInfo,tv,movieOrTvMyList
                ,myListMovieModel,searchModel);

        binding.detailAddToMyList.setOnClickListener(v -> {

            fireBaseHelper.addToMyList(v);
            binding.detailAddToMyList.setVisibility(View.GONE);
            binding.detailRemoveFromMyList.setVisibility(View.VISIBLE);

        });

        binding.detailRemoveFromMyList.setOnClickListener(v -> {

            fireBaseHelper.removeFromMyList(v, id);
            binding.detailAddToMyList.setVisibility(View.VISIBLE);
            binding.detailRemoveFromMyList.setVisibility(View.GONE);

        });


        binding.watchTrailer.setOnClickListener(v -> {

            Intent intent = new Intent(DetailActivity.this, VideosActivity.class);

            switch (incomeInfo) {
                case "normal":

                    intent.putExtra("new_info", "movie");
                    intent.putExtra("movie_video", movie);

                    break;
                case "tv":

                    intent.putExtra("new_info", "tv");
                    intent.putExtra("tv_video", tv);

                    break;
                case "myList":

                    if (tvOrMovieInfo.equals("movie")) {

                        intent.putExtra("new_info", "myList");
                        intent.putExtra("movie_or_tv_my_list", "movie_m");
                        intent.putExtra("movieOrTvId", myListMovieId);


                    } else if (tvOrMovieInfo.equals("tv")) {
                        intent.putExtra("new_info", "myList");
                        intent.putExtra("movie_or_tv_my_list", "tv_m");
                        intent.putExtra("movieOrTvId", myListTvId);

                    }

                    break;
            }
            startActivity(intent);

        });


    }

    public void checkExistingForDetail(String collectionPath,String fieldPath,int fieldId){

        CollectionReference collectionReference = firestore.collection(collectionPath);
        collectionReference.whereEqualTo(fieldPath, fieldId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (!task.getResult().isEmpty()) {
                    //has movie
                    binding.detailAddToMyList.setVisibility(View.GONE);
                    binding.detailRemoveFromMyList.setVisibility(View.VISIBLE);
                    for (DocumentSnapshot d : task.getResult().getDocuments()) {
                        id = d.getId();
                    }

                    binding.detailRemoveFromMyList.setOnClickListener(v -> {

                        fireBaseHelper.removeFromMyList(v, id);
                        binding.detailAddToMyList.setVisibility(View.VISIBLE);
                        binding.detailRemoveFromMyList.setVisibility(View.GONE);

                    });

                } else {
                    //has not movie
                    binding.detailAddToMyList.setVisibility(View.VISIBLE);
                    binding.detailRemoveFromMyList.setVisibility(View.GONE);

                    binding.detailAddToMyList.setOnClickListener(v -> {

                        fireBaseHelper.addToMyList(v);
                        binding.detailAddToMyList.setVisibility(View.GONE);
                        binding.detailRemoveFromMyList.setVisibility(View.VISIBLE);

                    });

                }

            }

        });
    }


    private void tvDetailHandleResponse(TvDetail tvDetail) {

        binding.textViewDetailName.setText(tvDetail.getOriginal_name());

        binding.textViewDetailRuntime.setText("" + tvDetail.getNumber_of_seasons() + " Seasons");
        binding.textViewDetailDate.setText(tvDetail.getFirst_air_date().substring(0, 4));
        binding.textViewLanguage.setText(tvDetail.getOriginal_language().toUpperCase());
        binding.textViewDetailOverview.setText(tvDetail.getOverview());

        ArrayList<String> genres = new ArrayList<>();
        for (MovieGenres m :
                tvDetail.getGenres()) {
            genres.add(m.getName());
        }


        for (Seasons s :
                tvDetail.getSeasons()) {
            seasonNumber = s.getSeason_number();
        }

        if(isInMyListSection){
            binding.viewPagerDetail.setAdapter(new ViewPagerDetailAdapter(this, incomeMovieId, incomeTvId, incomeInfo, seasonNumber,"tvs"));
        }else{
            binding.viewPagerDetail.setAdapter(new ViewPagerDetailAdapter(this, incomeMovieId, incomeTvId, incomeInfo, seasonNumber,"tvs"));
        }


        binding.textviewDetailGenres.setText(genres.toString().substring(1, (genres.toString().length() - 1)));


        if (tvDetail.isIn_production()) {
            binding.textViewDetailAdult.setText("IP");
        } else {
            binding.textViewDetailAdult.setText("NP");
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        binding.textViewDetailVoteAvrange.setText(String.valueOf(decimalFormat.format(tvDetail.getVote_average())));


        String imageUrl = incomePosterPatch;
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original/" + imageUrl + "")
                .into(binding.imageViewDetail, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {


                    }
                });


    }

    private void movieDetailHandleResponse(MovieDetail movieDetail) {

        binding.textViewDetailName.setText(movieDetail.getOriginal_title());

        int hour = movieDetail.getRuntime() / 60;
        int minutes = (movieDetail.getRuntime() - (hour * 60));

        binding.textViewDetailRuntime.setText("" + String.valueOf(hour) + "h " + String.valueOf(minutes) + "m");
        binding.textViewDetailDate.setText(movieDetail.getRelease_date().substring(0, 4));
        binding.textViewLanguage.setText(movieDetail.getOriginal_language().toUpperCase());
        binding.textViewDetailOverview.setText(movieDetail.getOverview());

        ArrayList<String> genres = new ArrayList<>();
        for (MovieGenres m :
                movieDetail.getGenres()) {
            genres.add(m.getName());
        }
        binding.textviewDetailGenres.setText(genres.toString().substring(1, (genres.toString().length() - 1)));

        if (movieDetail.isAdult()) {
            binding.textViewDetailAdult.setText("18+");
        } else {
            binding.textViewDetailAdult.setText("13+");
        }

        if(isInMyListSection){
            binding.viewPagerDetail.setAdapter(new ViewPagerDetailAdapter(this, incomeMovieId, incomeTvId, incomeInfo, seasonNumber,"movie"));
        }else{
            binding.viewPagerDetail.setAdapter(new ViewPagerDetailAdapter(this, incomeMovieId, incomeTvId, incomeInfo, seasonNumber,"movie"));
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");

        binding.textViewDetailVoteAvrange.setText(String.valueOf(decimalFormat.format(movieDetail.getVote_average())));

        String imageUrl = incomePosterPatch;
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + imageUrl + "")
                .into(binding.imageViewDetail, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}