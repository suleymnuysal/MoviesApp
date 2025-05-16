package com.suleymanuysal.moviesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.databinding.ActivityMainBinding;
import com.suleymanuysal.moviesapp.databinding.MainBottomSheetBinding;
import com.suleymanuysal.moviesapp.fragment.FragmentMyList;
import com.suleymanuysal.moviesapp.fragment.FragmentHome;
import com.suleymanuysal.moviesapp.fragment.FragmentTvShows;
import com.suleymanuysal.moviesapp.fragment.NewAndHotFragment;
import com.suleymanuysal.moviesapp.fragment.SearchFragment;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment tempFragment;
    private MainBottomSheetBinding mBinding;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(MainActivity.this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();


        if (firebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }



        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, FragmentHome.newInstance()).commit();


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.botom_nav_home:
                    tempFragment = FragmentHome.newInstance();
                    break;

                case R.id.bottom_nav_tv_shows:
                    tempFragment = FragmentTvShows.newInstance();
                    break;

                case R.id.bottom_nav_new_and_hot:

                    tempFragment = NewAndHotFragment.newInstance();
                    break;

                case R.id.bottom_nav_fav:
                    tempFragment = FragmentMyList.newInstance();
                    break;


            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, tempFragment).addToBackStack(null).commit();

            return true;

        });

       binding.include.mainOptionsMenu.setOnClickListener(v -> showBottomSheet());

        binding.include.mainSearch.setOnClickListener(v -> {

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, SearchFragment.newInstance()).addToBackStack(null).commit();

        });


    }

    private void showBottomSheet() {

        BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialogTheme);

        mBinding = MainBottomSheetBinding.inflate(LayoutInflater.from(MainActivity.this));

        mBinding.mainLogOut.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        mBinding.mainAbout.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);


        });

        bottomSheetDialog1.setContentView(mBinding.getRoot());
        bottomSheetDialog1.show();

    }

}