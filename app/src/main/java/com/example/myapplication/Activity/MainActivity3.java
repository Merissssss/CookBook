package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Activity.FavFragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMain3Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity3 extends AppCompatActivity {
    ActivityMain3Binding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final int homeNavId = R.id.home_nav;
        final int potNavId = R.id.pot_nav;
        final int ahahaNavId = R.id.ahaha_nav;
        final int profNavId = R.id.prof_nav;

        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setBackground(null);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == homeNavId) {
                replaceFragment(new HomeFragment());
            } else if (itemId == potNavId) {
                replaceFragment(new FavFragment());
            } else if (itemId == ahahaNavId) {
                replaceFragment(new CookFragment());
            } else if (itemId == profNavId) {
                replaceFragment(new MoreFragment());
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
