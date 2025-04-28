package com.suleymanuysal.moviesapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.suleymanuysal.moviesapp.activity.MainActivity;
import com.suleymanuysal.moviesapp.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private FirebaseAuth auth;
    private String userEmail;
    private String password;

    public static Fragment newInstance() {
        return new SignUpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();

        binding.imageViewsignUpBack.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(com.suleymanuysal.moviesapp.R.id.loginFrameLayout, SignInFragment.newInstance()).commit();

        });

        binding.editTextUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userEmail = s.toString();

                if (userEmail.isEmpty()) {
                    binding.textInputLayoutEmail.setError("*Field can't be empty");
                    binding.buttonSignUp.setClickable(false);
                } else {
                    binding.textInputLayoutEmail.setError("");
                    binding.buttonSignUp.setClickable(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();

                if (password.length() < 6 && password.length() != 0) {
                    binding.textInputLayoutPassword.setError("*Password has to be minimum 6 character");
                    binding.buttonSignUp.setClickable(false);
                } else if (password.isEmpty()) {
                    binding.textInputLayoutPassword.setError("*Field can't be empty");
                    binding.buttonSignUp.setClickable(false);
                } else {
                    binding.textInputLayoutPassword.setError("");
                    binding.buttonSignUp.setClickable(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        binding.buttonSignUp.setOnClickListener(v -> {

            String muserEmail = binding.editTextUserEmail.getText().toString();
            String mpassword = binding.editTextPassword.getText().toString();

            if (mpassword.isEmpty() || muserEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid information", Toast.LENGTH_LONG).show();
            } else {
                auth.createUserWithEmailAndPassword(muserEmail, mpassword).addOnSuccessListener((Activity) getContext(), new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Toast.makeText(getContext(), "User has been created successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "User could not be created because of " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                });
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}