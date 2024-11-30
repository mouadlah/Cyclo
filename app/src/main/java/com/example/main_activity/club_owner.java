package com.example.main_activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

public class club_owner extends AppCompatActivity {

    private Button next;
    private EditText dateEditText;
    private EditText capacityEditText;
    private EditText locationEditText;
    private EditText nameEditText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private String username; // This will be set from the logged-in user's information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_owner);

        // Initialize Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Retrieve the username from the Intent
        Intent intent = getIntent();
        if (intent.hasExtra("userName")) {
            username = intent.getStringExtra("userName");
        } else {
            Toast.makeText(this, "Username is not found. User must be logged in.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no username is found
            return;
        }

        // Reference to the events node under this user in Firebase
        databaseReference = firebaseDatabase.getReference("user").child(username).child("events");

        // Initialize UI components
        locationEditText = findViewById(R.id.place);
        capacityEditText = findViewById(R.id.capacity);
        dateEditText = findViewById(R.id.date);
        nameEditText=findViewById(R.id.name);
        next = findViewById(R.id.next);

        // Set up the date picker for the dateEditText
        setUpDatePicker();

        // When the 'next' button is clicked, add the event under the user's events
        next.setOnClickListener(v -> {
            String location = locationEditText.getText().toString().trim();
            String capacity = capacityEditText.getText().toString().trim();
            String dateString = dateEditText.getText().toString().trim();
            String name= nameEditText.getText().toString().trim();

            if (location.isEmpty() || capacity.isEmpty() || dateString.isEmpty()) {
                Toast.makeText(club_owner.this, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!capacity.matches("[0-9]+")) {
                Toast.makeText(club_owner.this, "Capacity must be a numeric value.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert the date to a Firebase-friendly key format
            String eventKey = dateString.replace("/", "_");
            Event event = new Event(capacity, dateString, location, name); // Pass the date in the format "dd/MM/yyyy"

            // Save the event under the 'events' node for this user
            databaseReference.child(eventKey).setValue(event)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(club_owner.this, "Event added successfully", Toast.LENGTH_SHORT).show();

                        // Start the choose_event activity and pass the eventKey along with it
                        Intent chooseEventIntent = new Intent(club_owner.this, choose_event.class);
                        chooseEventIntent.putExtra("date", eventKey);// Pass the event key to the next activity
                        chooseEventIntent.putExtra("userName", username);
                        startActivity(chooseEventIntent);

                        // Optionally finish the current activity if you no longer need it
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(club_owner.this, "Failed to add event: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }

    private void setUpDatePicker() {
        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            DatePickerDialog picker = new DatePickerDialog(club_owner.this, (view, year1, monthOfYear, dayOfMonth) -> {
                dateEditText.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year1)); // Ensure leading zeros are added for single digit day/month
            }, year, month, day);
            picker.show();
        });
    }

    public static class Event {
        public String capacity;
        public String date;
        String name;
        public String location;

        // No-argument constructor
        public Event() {
            // Default constructor required for calls to DataSnapshot.getValue(Event.class)
        }

        public Event(String capacity, String date, String location, String name) {
            this.capacity = capacity;
            this.date = date;
            this.location = location;
            this.name=name;
        }

        // Getters for the properties
        public String getCapacity() {
            return capacity;
        }
        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getLocation() {
            return location;
        }
    }

    // Rest of your class...
}
