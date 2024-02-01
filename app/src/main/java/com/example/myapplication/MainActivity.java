package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

//    public void validInput() {
//        String givenEmail = editTextEmail.getText().toString().trim();
//        String givenPassword = editTextPassword.getText().toString().trim();
//
//        String email = "arevshatyanmary@gmail.com";
//        String password = "12345678";
//
//        if (!givenEmail.matches(email)) {
//            editTextEmail.setError("Enter a valid email address");
//            Toast.makeText(getApplicationContext(), "Enter a valid email address", Toast.LENGTH_SHORT).show();
//        }
//        if (!givenPassword.matches(password)) {
//            editTextPassword.setError("Password should be at least 8 characters");
//            Toast.makeText(getApplicationContext(), "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
//        }
//        if (givenPassword.length() < 8) {
//            editTextPassword.setError("Password should be at least 8 characters");
//            Toast.makeText(getApplicationContext(), "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
//        } else if (givenEmail.matches(email) && givenPassword.matches(password) && givenPassword.length()==8){
//            openActivity3();
//        }
//    }

    public void openActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}