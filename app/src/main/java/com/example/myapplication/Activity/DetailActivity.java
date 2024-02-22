package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRecipeBinding;
import com.example.myapplication.domain.Domain;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {

    private ActivityRecipeBinding binding;
    private Domain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        getBundles();
    }

    private void getBundles() {
        object = (Domain) getIntent().getSerializableExtra("object");

        int drawableRecourseId = this.getResources().getIdentifier(String.valueOf(object.getPicUrl()),"drawable", this.getPackageName());

        Glide.with(this).load(drawableRecourseId).into(binding.imageView8);

        binding.tittleText.setText(object.getTitle());
        binding.recipeView.setText(object.getRecipe());
        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent
            }
        });

        binding.imageView.setOnClickListener(v -> finish());

    }
}