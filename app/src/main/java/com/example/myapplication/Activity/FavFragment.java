package com.example.myapplication.Activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.domain.AddRecipeModel;
import com.example.myapplication.databinding.FragmentFavBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavFragment extends Fragment {
    private static final String TAG_FAVORITE = "Favorite: ";
    private FragmentFavBinding binding;
    private Foods productAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<AddRecipeModel> favoriteList;

    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
