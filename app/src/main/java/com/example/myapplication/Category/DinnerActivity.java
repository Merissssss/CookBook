package com.example.myapplication.Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.R;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DinnerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView backBtn;
    private FirebaseFirestore db;
    private Foods adapter;
    private ArrayList<AddRecipeModel> dinnerRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        db = FirebaseFirestore.getInstance();
        dinnerRecipes = new ArrayList<>();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.dinnerRecipes);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        adapter = new Foods(dinnerRecipes);
        recyclerView.setAdapter(adapter);

        fetchDinnerRecipes();
    }

    private void fetchDinnerRecipes() {
        CollectionReference recipesRef = db.collection("Recipes");
        recipesRef.whereEqualTo("category", "Dinner")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddRecipeModel recipe = document.toObject(AddRecipeModel.class);
                                dinnerRecipes.add(recipe);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("DinnerActivity", "Error getting documents.", task.getException());
                            Toast.makeText(DinnerActivity.this, "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
