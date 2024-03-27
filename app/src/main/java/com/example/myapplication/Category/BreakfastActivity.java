package com.example.myapplication.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.R;
import com.example.myapplication.domain.Domain;

import java.util.ArrayList;

public class BreakfastActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        recyclerView = findViewById(R.id.breakfastRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);


        ArrayList<Domain> items = new ArrayList<>();
        items.add(new Domain("Pizza", R.drawable.pizza, "1. 500 grams of flour \n 2. 1 teaspoon of yeast \n 3. 100-150grams of lukewarm water \n 4. 1 egg \n 5. half teaspoon of salt \n 6. 3 spoons of vegetable oil\n 7. mix it all up\n "));
        items.add(new Domain("Corn Dog", R.drawable.corn_dog, "cook"));
        items.add(new Domain("Ramen", R.drawable.ramen, "cook"));
        items.add(new Domain("Strawberry Ice-cream", R.drawable.strawberryicecream, "cook"));
        items.add(new Domain("Omelette", R.drawable.omelette, "cook"));
        items.add(new Domain("Soup", R.drawable.sup, "cook"));

        Foods foodsAdapter = new Foods(items);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(foodsAdapter);
    }
}
