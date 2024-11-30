package com.example.main_activity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;

public class UpdateEvent extends AppCompatActivity {

    protected EditText ageEditText;
    protected EditText paceEditText;
    protected EditText levelEditText;
    protected EditText capacityEditText;
    protected EditText locationEditText;
    protected RadioGroup eventTypeRadioGroup;
    protected Button updateButton;
    protected DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        Intent intent = getIntent();
        String username = intent.getStringExtra("userName");
        String eventKey = intent.getStringExtra("eventKey");

        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(username).child("events").child(eventKey);

        ageEditText = findViewById(R.id.idAge);
        paceEditText = findViewById(R.id.Pace);
        levelEditText = findViewById(R.id.Level);
        capacityEditText = findViewById(R.id.idCapacity);
        locationEditText = findViewById(R.id.idLocation);
        eventTypeRadioGroup = findViewById(R.id.radioGroupEventTypes);
        updateButton = findViewById(R.id.BtnUpdateEventDetails);

        updateButton.setOnClickListener(v -> {
            String age = ageEditText.getText().toString();
            String pace = paceEditText.getText().toString();
            String level = levelEditText.getText().toString();
            String capacity = capacityEditText.getText().toString();
            String location = locationEditText.getText().toString();

            // This will be null if no radio button is checked
            RadioButton selectedRadioButton = findViewById(eventTypeRadioGroup.getCheckedRadioButtonId());
            String eventType = (selectedRadioButton != null) ? selectedRadioButton.getText().toString() : "";

            // Validation for numeric fields
            // Validate each field individually if it is not empty
            if (!age.isEmpty() && !age.matches("[0-9]+")) {
                Toast.makeText(UpdateEvent.this, "Age must be a numeric value.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pace.isEmpty() && !pace.matches("[0-9]+")) {
                Toast.makeText(UpdateEvent.this, "Pace must be numeric.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!level.isEmpty() && !level.matches("[0-9]+")) {
                Toast.makeText(UpdateEvent.this, "Level must be numeric.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!capacity.isEmpty() && !capacity.matches("[0-9]+")) {
                Toast.makeText(UpdateEvent.this, "Capacity must be numeric.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if at least one field is filled out
            if (!age.isEmpty() || !pace.isEmpty() || !level.isEmpty() || !capacity.isEmpty() || !location.isEmpty() || !eventType.isEmpty()) {
                if (!age.isEmpty()) databaseReference.child("age").setValue(age);
                if (!pace.isEmpty()) databaseReference.child("pace").setValue(pace);
                if (!level.isEmpty()) databaseReference.child("level").setValue(level);
                if (!capacity.isEmpty()) databaseReference.child("capacity").setValue(capacity);
                if (!location.isEmpty()) databaseReference.child("location").setValue(location);
                // Only update eventType if a selection has been made
                if (!eventType.isEmpty()) databaseReference.child("typeName").setValue(eventType);

                Toast.makeText(UpdateEvent.this, "Event details updated!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UpdateEvent.this, "Enter at least one value", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
