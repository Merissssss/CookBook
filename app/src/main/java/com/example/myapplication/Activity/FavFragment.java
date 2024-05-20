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
import com.example.myapplication.R;
import com.example.myapplication.domain.AddRecipeModel;
import com.example.myapplication.databinding.FragmentFavBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class FavFragment extends Fragment {
    private static final String TAG = "FavFragment";
    private FragmentFavBinding binding;
    private Foods adapter;
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
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        favoriteList = new ArrayList<>();

        RecyclerView recyclerView = binding.favRecipes;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new Foods((ArrayList<AddRecipeModel>) favoriteList);
        recyclerView.setAdapter(adapter);

        fetchFavoriteRecipes();

        return view;
    }

    private void fetchFavoriteRecipes() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        favoriteList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AddRecipeModel recipe = document.toObject(AddRecipeModel.class);
                            favoriteList.add(recipe);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }


}
