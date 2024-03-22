package com.example.myapplication.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final int breakfastNavId = R.id.breakfastText2;
        final int dinnerNavId = R.id.dinnerText2;
        final int supperNavId = R.id.supperText2;
        final int desertNavId = R.id.desertText2;

        replaceFragment(new BreakfastFragment());
        binding.bottomNavigation.setBackground(null);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == breakfastNavId) {
                replaceFragment(new BreakfastFragment());
            } else if (itemId == dinnerNavId) {
                replaceFragment(new DinnerFragment());
            } else if (itemId == supperNavId) {
                replaceFragment(new SupperFragment());
            } else if (itemId == desertNavId) {
                replaceFragment(new DesertFragment());
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}