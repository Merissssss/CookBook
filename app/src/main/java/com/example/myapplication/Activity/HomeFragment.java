package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.Category.BreakfastActivity;
import com.example.myapplication.Category.DessertActivity;
import com.example.myapplication.Category.DinnerActivity;
import com.example.myapplication.Category.LunchActivity;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private ArrayList<AddRecipeModel> listOfRecipes = new ArrayList<>();
    private Foods recipeAdapter;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Check if user is logged in
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            redirectToLogin();
            return view;
        }

        setupCategoryIcons();
        setupRecyclerView();
        setupSearchView();

        return view;
    }

    private void redirectToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void setupCategoryIcons() {
        binding.breakfastIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), BreakfastActivity.class)));
        binding.dinnerIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), LunchActivity.class)));
        binding.supperIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), DinnerActivity.class)));
        binding.desertIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), DessertActivity.class)));
    }

    private void setupRecyclerView() {
        db = FirebaseFirestore.getInstance();
        getRecipesFromFirestore();
        recipeAdapter = new Foods(listOfRecipes);
        binding.recipesView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recipesView.setAdapter(recipeAdapter);
    }

    @SuppressLint("RestrictedApi")
    private void setupSearchView() {
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return false;
            }
        });
    }

    private void filterRecipes(String query) {
        List<AddRecipeModel> filteredList = new ArrayList<>();
        for (AddRecipeModel recipe : listOfRecipes) {
            if (recipe.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }
        recipeAdapter.updateList(new ArrayList<>(filteredList)); // Convert to ArrayList
    }

    private void getRecipesFromFirestore() {
        if (user == null) {
            return;
        }
        String userId = user.getUid();

        db.collection("Recipes").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                AddRecipeModel recipe = document.toObject(AddRecipeModel.class);
                                listOfRecipes.add(recipe);
                            }
                            checkLikedRecipes(userId);
                        } else {
                            Log.w(TAG, "Error getting documents: query snapshot is null");
                            showToast("Error retrieving data from Firestore");
                        }
                    } else {
                        Log.w(TAG, "Error getting documents", task.getException());
                        showToast("Error retrieving data from Firestore");
                    }
                });
    }

    private void checkLikedRecipes(String userId) {
        if (user == null) {
            return;
        }
        db.collection("users").document(userId).collection("favorites")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String likedProductId = document.getId();
                                for (AddRecipeModel recipe : listOfRecipes) {
                                    if (likedProductId.equals(recipe.getProductId())) {
                                        recipe.setLiked(true);
                                        break;
                                    }
                                }
                            }
                            recipeAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting liked recipes: query snapshot is null");
                        }
                    } else {
                        Log.w(TAG, "Error getting liked recipes", task.getException());
                    }
                });
    }

    private void showToast(String message) {
        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());
    }
}
