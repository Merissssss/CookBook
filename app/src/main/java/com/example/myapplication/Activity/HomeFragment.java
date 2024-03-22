package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapter.Foods;
import com.example.myapplication.Category.CategoryActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.domain.Domain;
import java.util.ArrayList;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    ImageView breakfast;
    ImageView dinner;
    ImageView supper;
    ImageView desert;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        breakfast = view.findViewById(R.id.breakfastIcon);
        dinner = view.findViewById(R.id.dinnerIcon);
        supper = view.findViewById(R.id.supperIcon);
        desert = view.findViewById(R.id.desertIcon);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });
        supper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });
        desert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.RecipesView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);


        ArrayList<Domain> items = new ArrayList<>();
        items.add(new Domain("Pizza", R.drawable.pizza, "1. 500 grams of flour \n 2. 1 teaspoon of yeast \n 3. 100-150grams of lukewarm water \n 4. 1 egg \n 5. half teaspoon of salt \n 6. 3 spoons of vegetable oil\n 7. mix it all up\n "));
        items.add(new Domain("Corn Dog", R.drawable.corn_dog, "cook"));
        items.add(new Domain("Ramen", R.drawable.ramen, "cook"));
        items.add(new Domain("Strawberry Ice-cream", R.drawable.strawberryicecream, "cook"));
        items.add(new Domain("Omelette", R.drawable.omelette, "cook"));
        items.add(new Domain("Soup", R.drawable.sup, "cook"));

        Foods foodsAdapter = new Foods(items);


        recyclerView.setAdapter(foodsAdapter);
        return view;
    }
}
