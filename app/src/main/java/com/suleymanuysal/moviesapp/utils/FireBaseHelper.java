package com.suleymanuysal.moviesapp.utils;


import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.suleymanuysal.moviesapp.R;
import com.suleymanuysal.moviesapp.adapter.MyListRvAdapter;
import com.suleymanuysal.moviesapp.databinding.HomeBottomSheetDesignBinding;
import com.suleymanuysal.moviesapp.databinding.MyListFragmentBinding;
import com.suleymanuysal.moviesapp.model.SearchModel;
import com.suleymanuysal.moviesapp.model.movie.MyListMovieModel;
import com.suleymanuysal.moviesapp.model.movie.Movie;
import com.suleymanuysal.moviesapp.model.tv.Tv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FireBaseHelper {

    private FirebaseFirestore firestore;
    private String mdocumentReferenceId;
    private Context context;
    private HomeBottomSheetDesignBinding binding;
    private Movie movie;
    private MyListMovieModel myListMovieModel;
    private SearchModel searchModel;
    private String incomeInfo;
    private String movieOrTv;
    private Tv tv;
    private String id = null;
    private CollectionReference collectionReference,collectionReference2;
    private ArrayList<MyListMovieModel> movieModelArrayList;
    private MyListRvAdapter madapter;
    private String where;
    private String whereForRemove;

    public FireBaseHelper(FirebaseFirestore firestore, Context context, HomeBottomSheetDesignBinding binding, Movie movie, String incomeInfo, Tv tv, String movieOrTv, MyListMovieModel myListMovieModel, SearchModel searchModel) {
        this.firestore = firestore;
        this.context = context;
        this.binding = binding;
        this.movie = movie;
        this.incomeInfo = incomeInfo;
        this.tv = tv;
        this.movieOrTv = movieOrTv;
        this.myListMovieModel = myListMovieModel;
        this.searchModel = searchModel;
    }


    public void addToMyList(View view){

        HashMap<String,Object> postData = new HashMap<>();


        switch (incomeInfo) {
            case "normal":
                //Save Movie
                postData.put("movieId", movie.getId());
                postData.put("movieName", movie.getOriginal_title());
                postData.put("moviePosterPath", movie.getPoster_path());
                postData.put("movieReleaseDate", movie.getRelease_date());
                postData.put("movieVoteAverage", movie.getVote_average());
                postData.put("date", FieldValue.serverTimestamp());

                where = "MyListMovies";


                break;
            case "tv":
                //Save Tv
                postData.put("tvId", tv.getId());
                postData.put("tvName", tv.getOriginal_name());
                postData.put("tvPosterPath", tv.getPoster_path());
                postData.put("tvReleaseDate", tv.getFirst_air_date());
                postData.put("tvVoteAverage", tv.getVote_average());
                postData.put("date", FieldValue.serverTimestamp());

                where = "MyListTvs";


                break;
            case "myList":


                if (movieOrTv.equals("movie")) {

                    postData.put("movieId", myListMovieModel.getMovieId());
                    postData.put("movieName", myListMovieModel.getName());
                    postData.put("moviePosterPath", myListMovieModel.getPosterPath());
                    postData.put("movieReleaseDate", myListMovieModel.getReleaseDate());
                    postData.put("movieVoteAverage", myListMovieModel.getVoteAverage());
                    postData.put("date", FieldValue.serverTimestamp());

                    where = "MyListMovies";

                } else if (movieOrTv.equals("tv")) {

                    postData.put("tvId", myListMovieModel.getMovieId());
                    postData.put("tvName", myListMovieModel.getName());
                    postData.put("tvPosterPath", myListMovieModel.getPosterPath());
                    postData.put("tvReleaseDate", myListMovieModel.getReleaseDate());
                    postData.put("tvVoteAverage", myListMovieModel.getVoteAverage());
                    postData.put("date", FieldValue.serverTimestamp());

                    where = "MyListTvs";


                }
            case "search":

                if (movieOrTv.equals("movie")) {

                    postData.put("movieId", searchModel.getId());
                    postData.put("movieName", searchModel.getOriginal_title());
                    postData.put("moviePosterPath", searchModel.getPoster_path());
                    postData.put("movieReleaseDate", searchModel.getRelease_date());
                    postData.put("movieVoteAverage", searchModel.getVote_average());
                    postData.put("date", FieldValue.serverTimestamp());

                    where = "MyListMovies";

                } else if (movieOrTv.equals("tv")) {

                    postData.put("movieId", searchModel.getId());
                    postData.put("movieName", searchModel.getOriginal_name());
                    postData.put("moviePosterPath", searchModel.getPoster_path());
                    postData.put("movieReleaseDate", searchModel.getFirst_air_date());
                    postData.put("movieVoteAverage", searchModel.getVote_average());
                    postData.put("date", FieldValue.serverTimestamp());

                    where = "MyListTvs";


                }

                break;
        }



       firestore.collection(where).add(postData).addOnSuccessListener(documentReference -> {

            mdocumentReferenceId = documentReference.getId();

            Snackbar snackbar = Snackbar.make(view,"Successfully added to My List", LENGTH_SHORT);
            snackbar.setBackgroundTint(view.getResources().getColor(R.color.snack_bar));
            snackbar.setTextColor(view.getResources().getColor(R.color.black)).show();

       }).addOnFailureListener(e -> Toast.makeText(view.getContext(), "Could not be added because of "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());


    }

    public void removeFromMyList(View view,String documentReferenceId){

        String lasId = null;

        if(mdocumentReferenceId == null){
            lasId = documentReferenceId;
        }else {
            lasId = mdocumentReferenceId;

        }


        switch (incomeInfo) {
            case "normal":
                whereForRemove = "MyListMovies";

                break;
            case "tv":
                whereForRemove = "MyListTvs";

                break;
            case "myList":

                if (movieOrTv.equals("movie")) {
                    whereForRemove = "MyListMovies";

                } else if (movieOrTv.equals("tv")) {
                    whereForRemove = "MyListTvs";

                }

            case "search":

                if (movieOrTv.equals("movie")) {
                    whereForRemove = "MyListMovies";

                } else if (movieOrTv.equals("tv")) {
                    whereForRemove = "MyListTvs";

                }

                break;
        }



        firestore.collection(whereForRemove).document(lasId).delete()
                .addOnSuccessListener(unused -> {

                    Snackbar snackbar = Snackbar.make(view,"Successfully removed from My List", BaseTransientBottomBar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(view.getResources().getColor(R.color.snack_bar));
                    snackbar.setTextColor(view.getResources().getColor(R.color.black)).show();

                }).addOnFailureListener(e -> Toast.makeText(view.getContext(),"Could not be removed from My List", Toast.LENGTH_SHORT).show());




    }


    public void checkExisting(int movieId, View view, BottomSheetDialog bottomSheetDialog, String field, String collectionPath){


        firestore.collection(collectionPath).whereEqualTo(field, movieId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (!task.getResult().isEmpty()) {
                    //has movie
                    for (DocumentSnapshot d : task.getResult().getDocuments()) {
                        id = d.getId();
                    }

                    binding.homeBsRemoveFromMyList.setVisibility(View.VISIBLE);
                    binding.homeBsAddToMyList.setVisibility(View.GONE);

                    binding.homeBsRemoveFromMyList.setOnClickListener(v -> {
                        removeFromMyList(view, id);
                        bottomSheetDialog.cancel();
                    });




                } else {
                    //has not movie

                    binding.homeBsAddToMyList.setVisibility(View.VISIBLE);
                    binding.homeBsRemoveFromMyList.setVisibility(View.GONE);

                    binding.homeBsAddToMyList.setOnClickListener(v -> {
                        addToMyList(view);
                        bottomSheetDialog.cancel();
                    });


                }
            }


        });

    }


    public void getMyList(MyListFragmentBinding mbinding){

        if(mbinding != null){

            mbinding.recyclerViewMyList.setHasFixedSize(true);
            mbinding.recyclerViewMyList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }

       movieModelArrayList = new ArrayList<>();

       collectionReference = firestore.collection("MyListMovies");
       collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

               if(error != null){
                   Toast.makeText(context,"An Error Accord",Toast.LENGTH_SHORT).show();

               }else {
                   if (value != null){

                       for (QueryDocumentSnapshot dataFromFirebase:value
                            ) {
                           long movieId =0;

                           if (dataFromFirebase.get("movieId") != null) {
                               movieId = ((Long) Objects.requireNonNull(dataFromFirebase.get("movieId")));

                           } else {
                               Toast.makeText(context, "No movieId", Toast.LENGTH_SHORT).show();
                           }

                           String posterPath = (String) dataFromFirebase.get("moviePosterPath");
                           String name = (String) dataFromFirebase.get("movieName");
                           String releaseDate = (String) dataFromFirebase.get("movieReleaseDate");
                           double voteAverage = (double) dataFromFirebase.get("movieVoteAverage");

                           MyListMovieModel myListMovieModel = new MyListMovieModel(posterPath, name, releaseDate, movieId, "movie",voteAverage);
                           myListMovieModel.setMovieOrTv("movie");
                           movieModelArrayList.add(myListMovieModel);



                       }

                       if(movieModelArrayList.size()>0){
                           if(mbinding != null){

                               madapter = new MyListRvAdapter(context,movieModelArrayList);

                               mbinding.recyclerViewMyList.setAdapter(madapter);
                               mbinding.textViewMyList.setVisibility(View.VISIBLE);
                               mbinding.recyclerViewMyList.setVisibility(View.VISIBLE);

                           }
                       }else{
                           Log.d("TagMyList","null list");
                       }

                   }
               }

           }
       });


        //----------------------------------------- For TVs --------------------------------------------------------------

        collectionReference2 = firestore.collection("MyListTvs");

        collectionReference2.orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Map<String,Object> dataFromFirebase = document.getData();
                        long movieId =0;

                        if(!dataFromFirebase.isEmpty()) {

                            if (dataFromFirebase.get("tvId") != null) {
                                movieId = ((Long) Objects.requireNonNull(dataFromFirebase.get("tvId")));

                            } else {
                                Toast.makeText(context, "No tvId", Toast.LENGTH_SHORT).show();
                            }
                            String posterPath = (String) dataFromFirebase.get("tvPosterPath");
                            String name = (String) dataFromFirebase.get("tvName");
                            String releaseDate = (String) dataFromFirebase.get("tvReleaseDate");
                            double voteAverage = (double) dataFromFirebase.get("tvVoteAverage");

                            MyListMovieModel myListMovieModel = new MyListMovieModel(posterPath, name, releaseDate, movieId, "tv",voteAverage);
                            myListMovieModel.setMovieOrTv("tv");
                            movieModelArrayList.add(myListMovieModel);
                        }else {
                            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
                        }


                    }
                    if(movieModelArrayList.size()>0){
                        if(mbinding != null){

                            madapter = new MyListRvAdapter(context,movieModelArrayList);
                            mbinding.recyclerViewMyList.setAdapter(madapter);
                            mbinding.textViewMyList.setVisibility(View.VISIBLE);
                            mbinding.recyclerViewMyList.setVisibility(View.VISIBLE);

                        }

                    }else{
                        Log.d("TagMyList","null list");
                    }

                }else{

                    Log.d("getMyListFromDb","it is not successfully");
                }
            }
        });
    }

}
