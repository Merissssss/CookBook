package com.example.myapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.myapplication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthUtils {

    public static FirebaseUser checkUserAuthentication(Context context) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Log.d("AuthUtils", "User is not logged in, redirecting to login");
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
        return user;
    }
}
