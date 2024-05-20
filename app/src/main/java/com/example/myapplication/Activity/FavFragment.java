package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.R;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class FavFragment extends Fragment {

    private RecyclerView recyclerView;
    private Foods adapter;
    private ArrayList<AddRecipeModel> favoriteList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        recyclerView = view.findViewById(R.id.favRecipes); // Ensure this ID matches the XML layout
        if (recyclerView == null) {
            Log.e("FavFragment", "RecyclerView not found in layout.");
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        }

        favoriteList = new ArrayList<>();
        adapter = new Foods(favoriteList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        fetchFavoriteRecipes();

        return view;
    }

    private void fetchFavoriteRecipes() {
        String userId = mAuth.getCurrentUser().getUid();
        db.collection("users").document(userId)
                .collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        favoriteList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AddRecipeModel recipe = document.toObject(AddRecipeModel.class);
                            recipe.setLiked(true); // Mark the recipe as liked
                            favoriteList.add(recipe);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.w("FavFragment", "Error getting documents.", task.getException());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchFavoriteRecipes(); // Refresh the favorite list whenever the fragment resumes
    }
}
