package com.example.myapplication.Activity;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.myapplication.domain.Domain;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ImageView breakfast;
    ImageView dinner;
    ImageView supper;
    ImageView desert;
    ArrayList<Domain> listOfRecipes = new ArrayList<>();
    private RecyclerView recyclerViewRecipes;
    private FirebaseFirestore db;
    private Foods recipeAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        ////Category
        breakfast = view.findViewById(R.id.breakfastIcon);
        dinner = view.findViewById(R.id.dinnerIcon);
        supper = view.findViewById(R.id.supperIcon);
        desert = view.findViewById(R.id.desertIcon);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BreakfastActivity.class);
                startActivity(intent);
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LunchActivity.class);
                startActivity(intent);
            }
        });
        supper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DinnerActivity.class);
                startActivity(intent);
            }
        });
        desert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DessertActivity.class);
                startActivity(intent);
            }
        });





        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        getRecipe();

        recyclerViewRecipes = root.findViewById(R.id.recipesView);
        recipeAdapter = new Foods(listOfRecipes);
        LinearLayoutManager popularLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecipes.setLayoutManager(popularLayoutManager);
        recyclerViewRecipes.setAdapter(recipeAdapter);
        return view;
    }

    private void getRecipe() {
        listOfRecipes.clear();

        // Get the Firestore collection reference
        db.collection("recipes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            // Iterate through each document in the collection
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                Log.i(TAG, "Document data: " + document.getData());
                                // Parse the document data and add it to the ArrayList
                                String name = document.getString("name");
                                String description = document.getString("description");
                                String imageUrl = document.getString("imageUrl");
                                listOfRecipes.add(new Domain(name, description, imageUrl));
                            }
                            // Notify the adapter if needed
                            recipeAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents: query snapshot is null");
                            Toast.makeText(requireContext(), "Error getting data from Firestore", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                        Toast.makeText(requireContext(), "Error getting data from Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
