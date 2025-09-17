package com.example.workwithfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    public Button btnLogin, btnRegister;
    public EditText etEmail, etPassword;
    public HelperAuth firebaseHelper; // הכרזה על משתנה עזר
    private FirebaseAuth.AuthStateListener authStateListener;

    public void init()
    {
        btnLogin=findViewById(R.id.btnRLogin);
        btnRegister=findViewById(R.id.btnRRegister);
        etEmail=findViewById(R.id.etREmail);
        etPassword=findViewById(R.id.etRPassword);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        init();
        btnLogin.setEnabled(false);
        btnRegister.setEnabled(false);

        // נוסיף מאזין למצב ההתחברות
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                // המאזין הזה יופעל ברגע שפיירבייס מוכן
                firebaseHelper = HelperAuth.getInstance();
                btnLogin.setEnabled(true);
                btnRegister.setEnabled(true);
            }
        };
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseHelper = HelperAuth.getInstance();
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                Log.d("MARIELA","Register "+email);
                firebaseHelper.signUp(RegisterActivity.this,email,password );
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // נוסיף את המאזין כשפעילות מתחילה
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // נסיר את המאזין כשפעילות נפסקת כדי למנוע דליפות זיכרון
        if (authStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        }
    }
}