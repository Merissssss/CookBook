package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activity.MainActivity3;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SingUpActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button button;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextNumberPassword);
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.loginBtn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button = findViewById(R.id.signUpBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SingUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SingUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    sendEmailVerification(); // Send email verification
                                } else {
                                    Toast.makeText(SingUpActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Send email verification
    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SingUpActivity.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                openMainActivity3(); // Open MainActivity3 after verification email is sent
                            } else {
                                Toast.makeText(SingUpActivity.this, "Failed to send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void openMainActivity3() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
        finish();
    }
}
