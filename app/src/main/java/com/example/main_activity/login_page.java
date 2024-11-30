package com.example.main_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_page extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RadioGroup radioGroup;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private Button loginButton;
    private TextView Already;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        emailEditText = findViewById(R.id.email);
        loginButton = findViewById(R.id.login);
        radioGroup = findViewById(R.id.radioGroup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addUserToFirebase();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(login_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Already = (TextView) findViewById(R.id.Already);
        Already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(login_page.this,activity_login_page.class);
                startActivity(intent);
            }
        });
    }

    private void addUserToFirebase() throws IllegalArgumentException {
        // Get the selected RadioButton in the RadioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        // If no option is selected, selectedId will be -1
        if (selectedId == -1) {
            throw new IllegalArgumentException("Please select a user type");
        }
        String role = selectedRadioButton.getText().toString();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if any of the required fields are empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("All fields are required. Please fill out the email, username and password fields.");
        }

        // Create a new User object
        UserType newUser = new UserType(role, username, email, password);

        // Use 'code' as the key for the Firebase database entry
        databaseReference.child(username).setValue(newUser).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(login_page.this, "User creates successfully ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(login_page.this, "Failed to create user", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Data Model
    class UserType {
        public String role, username, email, password;

        public UserType(String role, String username, String email, String password) {
            this.role = role;
            this.username = username;
            this.email = email;
            this.password = password;

        }
    }
}
