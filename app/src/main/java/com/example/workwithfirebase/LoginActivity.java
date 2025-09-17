package com.example.workwithfirebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

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

public class LoginActivity extends AppCompatActivity {

    public Button btnLogin, btnRegister;
    public EditText etEmail, etPassword;
    // הכרזה על משתני פיירבייס
    public HelperAuth firebaseHelper; // הכרזה על משתנה עזר
    private FirebaseAuth.AuthStateListener authStateListener;


    public void init()
    {
        btnLogin=findViewById(R.id.btnLLogin);
        btnRegister=findViewById(R.id.btnLRegister);
        etEmail=findViewById(R.id.etLEmail);
        etPassword=findViewById(R.id.etLPassword);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // אתחול המשתנה firebaseHelper כאן

        init();
        // נשבית את הכפתורים עד שפיירבייס יאותחל
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
                firebaseHelper = HelperAuth.getInstance();

                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                Log.d("MARIELA","Login "+email);
                firebaseHelper.signIn(LoginActivity.this, email, password);


            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MARIELA","Go to register");
                Intent it=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
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