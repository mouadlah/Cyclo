package com.example.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class activity_login_page extends AppCompatActivity {
    DatabaseReference mDatabase;
    private EditText usernameEditText, passwordEditText;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_axtivity_login_page); // Ensure this layout name is correct
        mDatabase = FirebaseDatabase.getInstance().getReference("user"); // Assuming "user" is correct as per your database structure

        // Initialize EditText fields
        usernameEditText = findViewById(R.id.username); // ID should be as per your layout XML
        passwordEditText = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);

        // Set OnClickListener for the login button
        login.setOnClickListener(v -> attemptLogin());
    }

    public void attemptLogin() {
        final String username = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        mDatabase.child(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                if (dataSnapshot.exists()) {
                    String retrievedPassword = dataSnapshot.child("password").getValue(String.class);
                    if (password.equals(retrievedPassword)) {
                        // Check the role and redirect accordingly
                        String role = dataSnapshot.child("role").getValue(String.class);
                        navigateToRoleSpecificActivity(role, username);
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect password!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User not found!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToRoleSpecificActivity(String role, String username) {
        Intent intent = null;
        if ("Admin".equalsIgnoreCase(role)) {
            intent = new Intent(getApplicationContext(), AdminActivity.class);
        } else if ("Cycling Club".equalsIgnoreCase(role)) {
            intent = new Intent(getApplicationContext(), club.class); // Replace with the actual class name for the cycling club role
        } else if ("User".equalsIgnoreCase(role)) {
            intent = new Intent(getApplicationContext(), UserActivity.class);
        }

        if (intent != null) {
            intent.putExtra("userName", username); // Optional: Pass the username
            intent.putExtra("role", role); // Optional: Pass the role
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid role!", Toast.LENGTH_LONG).show();
        }
    }
    public boolean validate(String userName, String password) {
        return userName.equals("admin") && password.equals("admin");
    }

    public boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
