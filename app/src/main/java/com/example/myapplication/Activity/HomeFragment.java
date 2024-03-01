package com.example.myapplication.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.Domain;
import java.util.ArrayList;

// ... (other import statements)

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.RecipesView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        // Create a list of Domain items
        ArrayList<Domain> items = new ArrayList<>();
        items.add(new Domain("Pizza", R.drawable.pizza, "1. 500 grams of flour \n 2. 1 teaspoon of yeast \n 3. 100-150grams of lukewarm water \n 4. 1 egg \n 5. half teaspoon of salt \n 6. 3 spoons of vegetable oil\n 7. mix it all up\n "));
        items.add(new Domain("Corn Dog", R.drawable.corn_dog, "cook"));
        items.add(new Domain("Ramen", R.drawable.ramen, "cook"));

        // Create an instance of FoodsAdapter (assuming this is your adapter class)
        Foods foodsAdapter = new Foods(items);

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(foodsAdapter);

        return view;
    }
}
