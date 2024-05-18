package com.example.myapplication.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.R;
import com.example.myapplication.domain.AddRecipeModel;

import java.util.ArrayList;

public class LunchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.lunchRecipes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
    }
}
