package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Foods;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMain3Binding;
import com.example.myapplication.domain.Domain;


import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        ArrayList<Domain> items = new ArrayList<>();
        items.add(new Domain("Pizza", R.drawable.pizza,  "cook"));
        items.add(new Domain("Corn Dog", R.drawable.corn_dog, "cook"));
        items.add(new Domain("Ramen", R.drawable.ramen, "cook"));


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.home:
//                    replaceFragment(new HomeFragment());
//                    break;
//                case R.id.pot:
//                    replaceFragment(new SavedFragment());
//                    break;
//                case R.id.ahaha:
//                    replaceFragment(new CookFragment());
//                    break;
//                case R.id.prof:
//                    replaceFragment(new ProfileFragment());
//                    break;
//            }
            return true;
        });

        RecyclerView recyclerView = findViewById(R.id.RecipesView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new Foods(items));
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}