package com.example.workwithfirebase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class Auth extends Application {
    private static FirebaseAuth mAuth;

    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        // מייצרים את מופע ה-FirebaseAuth מיד לאחר מכן

    }

    public static FirebaseAuth getAuthInstance() {
        // אתחול עצלני: ניצור את המופע של FirebaseAuth רק אם הוא עדיין null
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }
}
