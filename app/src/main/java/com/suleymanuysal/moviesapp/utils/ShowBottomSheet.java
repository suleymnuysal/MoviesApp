package com.suleymanuysal.moviesapp.utils;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.google.firebase.firestore.FirebaseFirestore;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.model.movie.Movie;


public class ShowBottomSheet {


    public static void showBottomSheet(Movie movie, Context context, HomeBottomSheetDesignBinding binding,String incomeInfo,View view,int mMovieId,String fieldPath,String collectionPath) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        binding = HomeBottomSheetDesignBinding.inflate(LayoutInflater.from(context));


        binding.homeBsRemoveFromMyList.setVisibility(View.GONE);

        FireBaseHelper fireBaseHelper = new FireBaseHelper(FirebaseFirestore.getInstance(),context,binding,movie,incomeInfo,null,null,null,null);
        
        if(movie != null){
            fireBaseHelper.checkExisting(movie.getId(),view,bottomSheetDialog,fieldPath,collectionPath);
        }else{
            fireBaseHelper.checkExisting(mMovieId,view,bottomSheetDialog,fieldPath,collectionPath);
        }

        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.show();

    }


}
