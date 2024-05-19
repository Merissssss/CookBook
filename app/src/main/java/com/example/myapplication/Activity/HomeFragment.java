package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<AddRecipeModel> listOfRecipes = new ArrayList<>();
    private RecyclerView recyclerViewRecipes;
    private Foods recipeAdapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setupCategoryIcons();
        setupRecyclerView();
        setupSearchView(); // Add this line

        return view;
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

        recyclerViewRecipes = binding.recipesView;
        recipeAdapter = new Foods(listOfRecipes);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRecipes.setAdapter(recipeAdapter);
    }

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
        recipeAdapter.updateList(filteredList);
    }

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
                            showToast("Error retrieving data from Firestore");
                        }
                    } else {
                        showToast("Error retrieving data from Firestore");
                    }
                });
    }

    private void showToast(String message) {
        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show());
    }
}
