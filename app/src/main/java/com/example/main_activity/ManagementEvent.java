package com.example.main_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagementEvent extends AppCompatActivity {

    protected RadioGroup radioGroup;
    protected EditText codeNumber;
    protected EditText ageEditText;
    protected EditText levelEditText;
    protected EditText paceEditText;
    private Button createButton;
    private DatabaseReference databaseReference; // Firebase database reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_event);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EventTypes");

        // Initialize RadioGroup and EditTexts
        radioGroup = findViewById(R.id.radioGroup);
        codeNumber = findViewById(R.id.editTextText4);
        ageEditText = findViewById(R.id.editTextText5);
        levelEditText = findViewById(R.id.editTextText6);
        paceEditText = findViewById(R.id.editTextText3);
        createButton = findViewById(R.id.button2);

        // Set a click listener for the button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addEventTypeToFirebase();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(ManagementEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addEventTypeToFirebase() {
        try {
            EventType newEventType = createEventTypeFromInput();
            // Use 'code' as the key for the Firebase database entry
            databaseReference.child(newEventType.code).setValue(newEventType)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ManagementEvent.this, "Event type added successfully with code: " + newEventType.code, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManagementEvent.this, "Failed to add event type", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast.makeText(ManagementEvent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public EventType createEventTypeFromInput() throws IllegalArgumentException {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        if (selectedId == -1) {
            throw new IllegalArgumentException("Please select an event type.");
        }
        String typeName = selectedRadioButton.getText().toString();
        String code = codeNumber.getText().toString().trim();
        String age = ageEditText.getText().toString().trim();
        String level = levelEditText.getText().toString().trim();
        String pace = paceEditText.getText().toString().trim();

        if (code.isEmpty() || age.isEmpty() || level.isEmpty() || pace.isEmpty()) {
            throw new IllegalArgumentException("All fields are required. Please fill out the code, age, level, and pace fields.");
        }

        return new EventType(typeName, code, age, level, pace);
    }
}


// Data Model
class EventType {
    public String typeName, code, age, level, pace;

    public EventType(String typeName, String code, String age, String level, String pace) {
        this.typeName = typeName;
        this.code = code;
        this.age = age;
        this.level = level;
        this.pace = pace;
    }
}