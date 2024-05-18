package com.example.myapplication.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.databinding.ActivityRecipeBinding;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {

    private ActivityRecipeBinding binding;
    private AddRecipeModel object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String recipeId = getIntent().getStringExtra("recipeId"); // Use the correct key "recipeId"
        if (recipeId != null) {
            fetchRecipeFromFirestore(recipeId);
        } else {
            Toast.makeText(this, "Recipe ID is missing", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchRecipeFromFirestore(String recipeId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference recipeRef = db.collection("Recipes").document(recipeId);

        recipeRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    AddRecipeModel recipeModel = document.toObject(AddRecipeModel.class);
                    if (recipeModel != null) {
                        updateUI(recipeModel);
                    }
                } else {
                    Toast.makeText(this, "Recipe does not exist", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(this, "Error fetching recipe", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(AddRecipeModel recipeModel) {
        Glide.with(this)
                .load(recipeModel.getImageAlpha())
                .into(binding.imageView8);

        binding.tittleText.setText(recipeModel.getTitle());
    }
}
