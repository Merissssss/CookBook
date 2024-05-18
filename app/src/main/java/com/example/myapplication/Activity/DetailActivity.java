package com.example.myapplication.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
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

        getBundles();
    }

    private void getBundles() {
        object = (AddRecipeModel) getIntent().getSerializableExtra("recipeId");

        // Retrieve the resource ID for the drawable resource
//        int drawableResourceId = this.getResources().getIdentifier(object.getImageAlpha(), "drawable", this.getPackageName());

//        // Use Glide to load the drawable resource into an ImageView
//        Glide.with(this)
//                .load(Integer.parseInt(object.getImageAlpha())) // Assuming getImageAlpha() returns a resource ID
//                .into(binding.imageView8);

        // Set the title and location text
        binding.tittleText.setText(object.getName());
        binding.recipeView.setText(object.getDescription());
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set a click listener for the save button

    }
}
