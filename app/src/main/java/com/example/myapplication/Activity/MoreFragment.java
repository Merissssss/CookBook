package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MoreFragment extends Fragment {

    FirebaseAuth auth;
    TextView textView;
    TextView textView1;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        auth = FirebaseAuth.getInstance(); // Initialize FirebaseAuth

        ImageView imageView = view.findViewById(R.id.imageView2);
        ImageView imageView1 = view.findViewById(R.id.imageView3);
        textView1 = view.findViewById(R.id.textView5);
        textView = view.findViewById(R.id.textView7);
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

        user = auth.getCurrentUser();
        if (user != null) {
            textView.setText(user.getEmail());
        }

        return view;
    }

}
