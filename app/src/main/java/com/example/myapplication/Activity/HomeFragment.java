package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.example.myapplication.domain.Category;
import com.example.myapplication.domain.Domain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ImageView breakfast;
    ImageView dinner;
    ImageView supper;
    List<Domain> foodList;
    Foods foodAdapter;
    ImageView desert;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
        initGlobalFields();

        return view;
    }

    private void initGlobalFields() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        initRecyclerView();
    }

    private void fetchDataFromFirestore() {
        if (isAdded()) {
            foodList = new ArrayList<>(); // Initialize foodList

            CollectionReference recipesCollection = db.collection("Food");

            recipesCollection.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Domain recipe = document.toObject(Domain.class);
                        foodList.add(recipe);
                        foodAdapter.notifyItemInserted(foodList.size());
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data from Firestore", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void setProductRecycler(List<Domain> foodList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.RecipesView.setLayoutManager(layoutManager);
        foodAdapter = new Foods(requireContext(), foodList, R.layout.recipes);
        binding.RecipesView.setAdapter(foodAdapter);
    }
    private void fetchUnreadCount() {
        binding.unreadProgressBar.setVisibility(View.VISIBLE);

        db.collection("Notifications")
                .whereEqualTo("ownerId", currentUser.getUid())
                .whereEqualTo("read", false)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = task.getResult().size();
                        if (count != 0) {
                            binding.unreadCount.setText(String.valueOf(count));
                        }
                    } else {
                        Log.d("UnreadCount", "Error getting documents: ", task.getException());
                    }
                    binding.unreadProgressBar.setVisibility(View.GONE);
                    binding.notificationNumberContainer.setVisibility(View.VISIBLE);
                });
    }
    private void initRecyclerView() {
        foodList = new ArrayList<>();
        fetchDataFromFirestore();
        setProductRecycler(foodList);
        fetchUnreadCount();
    }

}
