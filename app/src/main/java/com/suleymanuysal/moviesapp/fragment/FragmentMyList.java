package com.suleymanuysal.moviesapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.suleymanuysal.moviesapp.databinding.MyListFragmentBinding;
import com.suleymanuysal.moviesapp.utils.FireBaseHelper;

public class FragmentMyList extends Fragment {

    private MyListFragmentBinding binding;
    private FirebaseFirestore firestore;
    private FireBaseHelper fireBaseHelper;



    public static Fragment newInstance() {
        return new FragmentMyList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyListFragmentBinding.inflate(inflater, container, false);

        firestore = FirebaseFirestore.getInstance();
        fireBaseHelper = new FireBaseHelper(firestore,getContext(),null,null,null,null,null,null,null);

        fireBaseHelper.getMyList(binding);

        binding.swipeRefreshlayoutForMylist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fireBaseHelper.getMyList(binding);
                binding.swipeRefreshlayoutForMylist.setRefreshing(false);
            }
        });


        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
