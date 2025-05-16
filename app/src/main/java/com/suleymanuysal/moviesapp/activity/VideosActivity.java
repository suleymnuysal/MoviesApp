package com.suleymanuysal.moviesapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.ActivityVideosBinding;
import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.model.movie.MovieVideos;
import com.suleymanuysal.moviesapp.model.movie.TvVideos;
import com.suleymanuysal.moviesapp.model.results.MovieVideosResult;
import com.suleymanuysal.moviesapp.model.results.TvVideosResult;
import com.suleymanuysal.moviesapp.model.tv.Tv;
import com.suleymanuysal.moviesapp.service.MovieAPI;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class VideosActivity extends AppCompatActivity {

    private ActivityVideosBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieAPI movieAPI;
    private Movie movie;
    private Tv tv;
    private String key;

    private boolean isFullScreen = false;
    private YouTubePlayer youTubePlayer;
    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (isFullScreen) {
                // if the player is in fullscreen, exit fullscreen
                youTubePlayer.toggleFullscreen();
            } else {
                finish();
            }

        }
    };
    private String incomeInfo;
    private int movieId;
    private int tvId;
    private int sTvId;
    private int seasonNumber;
    private int episodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textViewDataInfo.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController windowInsetsController = getWindow().getInsetsController();
            if (windowInsetsController != null) {
                windowInsetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        movieAPI = ApiUtils.getMovieApi(Credentials.MOVIE_BASE_URL);


        incomeInfo = getIntent().getStringExtra("new_info");

        if (incomeInfo.equals("movie")) {

            movie = (Movie) getIntent().getSerializableExtra("movie_video");
            movieId = movie.getId();


        } else if (incomeInfo.equals("tv")) {

            tv = (Tv) getIntent().getSerializableExtra("tv_video");
            tvId = tv.getId();

        }else if (incomeInfo.equals("tvLatest")) {

           Tv tvM = (Tv) getIntent().getSerializableExtra("tv_video");
           int tvIdm = tvM.getId();

            compositeDisposable.add(movieAPI.getAllTvVideos(tvIdm, Credentials.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorComplete()
                    .subscribe(this::videosTvHandleResponse));


        }else if (incomeInfo.equals("myList")) {


            String movieOrTv = getIntent().getStringExtra("movie_or_tv_my_list");

            if(movieOrTv.equals("movie_m")){
                int cmovieId = getIntent().getIntExtra("movieOrTvId",0);

                compositeDisposable.add(movieAPI.getAllMovieVideos(cmovieId, Credentials.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorComplete()
                        .subscribe(this::videosHandleResponse));

            } else if (movieOrTv.equals("tv_m")) {
                int cTvId = getIntent().getIntExtra("movieOrTvId",0);

                compositeDisposable.add(movieAPI.getAllTvVideos(cTvId, Credentials.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorComplete()
                        .subscribe(this::videosTvHandleResponse));


            }


        } else if (incomeInfo.equals("tv_episodes")) {

            sTvId = getIntent().getIntExtra("tv_video_id", 0);
            seasonNumber = getIntent().getIntExtra("tv_season_number", 1);
            episodeNumber = getIntent().getIntExtra("tv_episode_number", 1);

        }

        compositeDisposable.add(movieAPI.getAllMovieVideos(movieId, Credentials.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(this::videosHandleResponse));


        compositeDisposable.add(movieAPI.getAllTvVideos(tvId, Credentials.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(this::videosTvHandleResponse));


        compositeDisposable.add(movieAPI.getTvEpisodeVideos(sTvId, seasonNumber, episodeNumber, Credentials.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .subscribe(this::tvEpisodesVideosHandleResponse));


        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);


        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(1)
                // enable full screen button
                .fullscreen(1)
                .build();

        binding.videoView.setEnableAutomaticInitialization(false);

        binding.videoView.addFullscreenListener(new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@androidx.annotation.NonNull View view, @androidx.annotation.NonNull Function0<Unit> function0) {
                isFullScreen = true;
                binding.videoView.setVisibility(View.GONE);
                binding.videoViewContainer.setVisibility(View.VISIBLE);
                binding.videoViewContainer.addView(view);
            }

            @Override
            public void onExitFullscreen() {
                isFullScreen = false;
                binding.videoView.setVisibility(View.VISIBLE);
                binding.videoViewContainer.setVisibility(View.GONE);
                binding.videoViewContainer.removeAllViews();

            }
        });

        binding.videoView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@androidx.annotation.NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                VideosActivity.this.youTubePlayer = youTubePlayer;
                if (key != null) {
                    youTubePlayer.loadVideo(key, 0);
                } else {
                    binding.textViewDataInfo.setVisibility(View.VISIBLE);
                    binding.textViewDataInfo.setText("There is no video found");
                    Log.d("VideoError", "The key is null");
                }


            }
        }, iFramePlayerOptions);

        getLifecycle().addObserver(binding.videoView);

    }

    private void tvEpisodesVideosHandleResponse(TvVideosResult tvVideosResult) {
        if (tvVideosResult.getTvVideos().size() > 0) {

            for (TvVideos m :
                    tvVideosResult.getTvVideos()) {

                if (m.getSite().equals("YouTube")) {
                    key = m.getKey();
                } else {
                    binding.textViewDataInfo.setVisibility(View.VISIBLE);
                    binding.textViewDataInfo.setText("There is no video found");

                }

            }

        } else {
            binding.textViewDataInfo.setVisibility(View.VISIBLE);
            binding.textViewDataInfo.setText("There is no video found");


        }
    }


    private void videosTvHandleResponse(TvVideosResult tvVideosResult) {

        if (tvVideosResult.getTvVideos().size() > 0) {

            for (TvVideos m :
                    tvVideosResult.getTvVideos()) {

                if (m.getSite().equals("YouTube")) {
                    key = m.getKey();
                } else {
                    binding.textViewDataInfo.setVisibility(View.VISIBLE);
                    binding.textViewDataInfo.setText("There is no Trailer");


                }

            }

        } else {
            binding.textViewDataInfo.setVisibility(View.VISIBLE);
            binding.textViewDataInfo.setText("There is no Trailer");

        }

    }


    private void videosHandleResponse(MovieVideosResult movieVideosResult) {
        if (movieVideosResult.getGetMovieVideos().size() > 0) {

            for (MovieVideos m :
                    movieVideosResult.getGetMovieVideos()) {

                if (m.getSite().equals("YouTube")) {
                    key = m.getKey();
                } else {
                    binding.textViewDataInfo.setVisibility(View.VISIBLE);
                    binding.textViewDataInfo.setText("There is no Trailer");

                }

            }

        } else {
            binding.textViewDataInfo.setVisibility(View.VISIBLE);
            binding.textViewDataInfo.setText("There is no Trailer");

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        binding.videoView.release();
    }
}