package com.example.workwithfirebase;

import android.app.Activity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public   class HelperAuth {
    private static HelperAuth instance;
    private static FirebaseAuth mAuth;
    private HelperAuth() {

    }
    // A public static method that provides the single instance
    public static HelperAuth getInstance() {
        if (instance == null) {
            instance = new HelperAuth();
        }
        if(mAuth==null)
            mAuth = Auth.getAuthInstance();
        return instance;
    }

    public void signIn(Activity activity, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // הכניסה הצליחה
                        Toast.makeText(activity, "התחברת בהצלחה!", Toast.LENGTH_SHORT).show();
                        // לדוגמה, העברה למסך אחר:
                        // Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        // startActivity(intent);
                    } else {
                        // הכניסה נכשלה
                        Toast.makeText(activity, "כניסה נכשלה: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void signUp(Activity activity,String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // הרישום הצליח
                        // ניתן להעביר למסך הבא או להציג הודעה
                        Toast.makeText(activity, "רישום מוצלח!", Toast.LENGTH_SHORT).show();
                    } else {
                        // הרישום נכשל
                        Toast.makeText(activity, "רישום נכשל: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
