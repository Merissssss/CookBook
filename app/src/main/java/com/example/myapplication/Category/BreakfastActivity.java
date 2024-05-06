package com.example.myapplication.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.R;
import com.example.myapplication.domain.Domain;

import java.util.ArrayList;

public class BreakfastActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.breakfastRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


//        recyclerView.setAdapter(foodsAdapter);
    }
}
