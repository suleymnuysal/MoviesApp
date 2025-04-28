package com.suleymanuysal.moviesapp.fragment;

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
import com.google.firebase.auth.FirebaseUser;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.activity.MainActivity;
import com.suleymanuysal.moviesapp.databinding.FragmentSignInBinding;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private String userEmail;
    private String password;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static Fragment newInstance() {
        return new SignInFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }


        binding.textViewSignUp.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.loginFrameLayout, SignUpFragment.newInstance());
            fragmentTransaction.commit();

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
                    binding.buttonSignIn.setClickable(false);
                } else {
                    binding.textInputLayoutEmail.setError("");
                    binding.buttonSignIn.setClickable(true);
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

                if (password.isEmpty()) {
                    binding.textInputLayoutPassword.setError("*Field can't be empty");
                    binding.buttonSignIn.setClickable(false);
                } else {
                    binding.textInputLayoutPassword.setError("");
                    binding.buttonSignIn.setClickable(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        binding.buttonSignIn.setOnClickListener(v -> {

            String muserEmail = binding.editTextUserEmail.getText().toString();
            String mpassword = binding.editTextPassword.getText().toString();

            if (mpassword.isEmpty() || muserEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid information", Toast.LENGTH_LONG).show();
            } else {

                auth.signInWithEmailAndPassword(userEmail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        getContext().startActivity(intent);

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Operation failed " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }

        });

        return binding.getRoot();
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