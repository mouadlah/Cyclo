package com.example.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventForm extends AppCompatActivity {

    protected EditText editTextAge;
    protected EditText editTextCity;
    protected EditText editTextPhoneNumber;
    protected Spinner spinnerTShirtSize;
    protected RadioGroup radioGroupParticipation;
    protected Button buttonSubmit;

    protected DatabaseReference databaseRef;
    private String username; // Variable to store the username received from intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);

        // Retrieve the username from the previous activity

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("userName");
            if (username == null || username.trim().isEmpty()) {
                Toast.makeText(this, "No username provided.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            // Now you can use the username as needed
        }


        // Initialize Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("user").child(username);

        // Initialize UI elements
        editTextAge = findViewById(R.id.editTextAge);
        editTextCity = findViewById(R.id.editTextCity);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        spinnerTShirtSize = findViewById(R.id.spinnerTShirtSize);
        radioGroupParticipation = findViewById(R.id.radioGroupParticipation);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set up the button click listener
        buttonSubmit.setOnClickListener(view -> submitForm());
    }

    protected void submitForm() {
        String ageStr = editTextAge.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String tShirtSize = spinnerTShirtSize.getSelectedItem().toString();
        boolean hasParticipated = radioGroupParticipation.getCheckedRadioButtonId() == R.id.radioButtonYes;

        // Validate inputs
        if (!validateInputs(ageStr, phoneNumber)) {
            return;
        }

        int age = Integer.parseInt(ageStr); // Parse the age to an integer

        // Store the information under the user's node
        databaseRef.child("age").setValue(age);
        databaseRef.child("city").setValue(city);
        databaseRef.child("phoneNumber").setValue(phoneNumber);
        databaseRef.child("tShirtSize").setValue(tShirtSize);
        databaseRef.child("hasParticipated").setValue(hasParticipated);

        Toast.makeText(this, "Information submitted successfully!", Toast.LENGTH_LONG).show();
    }

    private boolean validateInputs(String ageStr, String phoneNumber) {
        if (ageStr.isEmpty()) {
            editTextAge.setError("Age is required.");
            return false;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0) {
                editTextAge.setError("Please enter a valid age.");
                return false;
            }
        } catch (NumberFormatException e) {
            editTextAge.setError("Please enter a valid number for age.");
            return false;
        }

        if (phoneNumber.isEmpty() || phoneNumber.length() != 10) {
            editTextPhoneNumber.setError("Phone number must be 10 digits.");
            return false;
        }

        return true;
    }
}
