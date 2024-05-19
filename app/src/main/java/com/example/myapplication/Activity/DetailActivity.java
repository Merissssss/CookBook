package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRecipeBinding;
import com.example.myapplication.domain.AddRecipeModel;

public class DetailActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
    private AddRecipeModel object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundles();
    }

    private void getBundles() {
        object = (AddRecipeModel) getIntent().getSerializableExtra("recipe"); // Use the correct key

        if (object != null) {
            // Load image using Glide
            Glide.with(this)
                    .load(object.getImageAlpha()) // Assuming getImageAlpha() returns a URL
                    .into(binding.imageView8);

            // Set the title and description text
            binding.tittleText.setText(object.getName());
            binding.recipeView.setText(object.getDescription());

            // Set a click listener for the close imageView
            binding.imageView.setOnClickListener(v -> finish());
        } else {
            // Handle the case where object is null
            Toast.makeText(this, "Error loading recipe details", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if the object is null
        }
    }
}
