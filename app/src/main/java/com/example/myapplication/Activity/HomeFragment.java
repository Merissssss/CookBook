package com.example.myapplication.Activity;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.Category.BreakfastActivity;
import com.example.myapplication.Category.DessertActivity;
import com.example.myapplication.Category.DinnerActivity;
import com.example.myapplication.Category.LunchActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private ArrayList<AddRecipeModel> listOfRecipes = new ArrayList<>();
    private RecyclerView recyclerViewRecipes;
    private Foods recipeAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setupCategoryIcons();
        setupRecyclerView();

        return view;
    }

    private void setupCategoryIcons() {
        binding.breakfastIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), BreakfastActivity.class)));
        binding.dinnerIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), LunchActivity.class)));
        binding.supperIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(),DinnerActivity.class)));
        binding.desertIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), DessertActivity.class)));
    }

    private void setupRecyclerView() {
        db = FirebaseFirestore.getInstance();
        getRecipesFromFirestore();

        recyclerViewRecipes = binding.recipesView;
        recipeAdapter = new Foods(listOfRecipes);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRecipes.setAdapter(recipeAdapter);
    }

    @SuppressLint("RestrictedApi")
    private void getRecipesFromFirestore() {
        db.collection("Recipes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                AddRecipeModel recipe = document.toObject(AddRecipeModel.class);
                                listOfRecipes.add(recipe);
                            }
                            recipeAdapter.notifyDataSetChanged();
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

    private void showToast(String message) {
        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());
    }
}
