package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.domain.AddRecipeModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    RecyclerView recyclerView;
    Foods foodsAdapter;
    ArrayList<AddRecipeModel> recipeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        recyclerView = view.findViewById(R.id.myRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recipeList = new ArrayList<>();
        foodsAdapter = new Foods(recipeList);
        recyclerView.setAdapter(foodsAdapter);

        ImageView imageView = view.findViewById(R.id.imageView2);
        ImageView imageView1 = view.findViewById(R.id.imageView3);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                startActivity(intent);
            }
        });

        if (user != null) {
            fetchUserRecipes();
        }

        return view;
    }

    private void fetchUserRecipes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = user.getUid();

        db.collection("users")
                .document(userId)
                .collection("MyRecipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddRecipeModel recipe = document.toObject(AddRecipeModel.class);
                                recipeList.add(recipe);
                            }
                            foodsAdapter.updateList(recipeList);
                        } else {
                            // Handle error
                        }
                    }
                });
    }
}
