package com.suleymanuysal.moviesapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.databinding.ActivityLoginBinding;
import com.suleymanuysal.moviesapp.fragment.SignInFragment;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().add(R.id.loginFrameLayout, SignInFragment.newInstance()).commit();


    }


}