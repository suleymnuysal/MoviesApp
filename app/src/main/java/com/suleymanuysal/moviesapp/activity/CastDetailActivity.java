package com.suleymanuysal.moviesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.apiUtils.ApiUtils;
import com.suleymanuysal.moviesapp.credentials.Credentials;
import com.suleymanuysal.moviesapp.databinding.ActivityCastDetailBinding;
import com.suleymanuysal.moviesapp.model.CastDetail;
import com.suleymanuysal.moviesapp.service.MovieAPI;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CastDetailActivity extends AppCompatActivity {

    private ActivityCastDetailBinding binding;
    private int personId;
    private MovieAPI movieAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCastDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieAPI = ApiUtils.getMovieApi(Credentials.PERSON_BASE_URL);

        personId = getIntent().getIntExtra("person_id",0);

        if(personId != 0) {

            compositeDisposable.add(movieAPI.getPersonDetail(personId, Credentials.API_KEY)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorComplete()
                    .subscribe(this::personDetailHandleResponse)

            );

        }else {
            Toast.makeText(this, "no personId", Toast.LENGTH_SHORT).show();
        }



    }

    private void personDetailHandleResponse(CastDetail castDetail) {

        if(castDetail.getName() != null){

            String imageUrl = castDetail.getProfile_path();

            Picasso.get()
                    .load("https://image.tmdb.org/t/p/original" + imageUrl + "")
                    .into(binding.imageViewPerson, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(R.drawable.place_holder_tv).into(binding.imageViewPerson);
                        }
                    });


            binding.textViewPersonName.setText(castDetail.getName());
            binding.textViewBiography.setText(castDetail.getBiography());
            binding.textViewPopularity.setText( "Popularity : "+String.valueOf(castDetail.getPopularity()));
            binding.textViewBirthDay.setText("Birth day : "+castDetail.getBirthday());
            binding.textViewPlaceOfBirth.setText("Place of birth : "+castDetail.getPlace_of_birth());


        }else {
            Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}