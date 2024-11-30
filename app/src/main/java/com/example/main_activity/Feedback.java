package com.example.main_activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.main_activity.UserActivity;

public class Feedback extends AppCompatActivity {
    RadioGroup radioGroup;
    EditText editTextFeedback;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        radioGroup = findViewById(R.id.radioGroup);
        editTextFeedback = findViewById(R.id.editTextFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndDisplayMessage();
            }
        });
    }
    void validateAndDisplayMessage() {
        int selectedRating = getSelectedRating();
        String feedback = editTextFeedback.getText().toString();

        if (TextUtils.isEmpty(feedback) || selectedRating == 0) {
            Toast.makeText(this, "Sorry, you need fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, activity_front_page.class);
            startActivity(intent);
            finish();
        }
    }
    int getSelectedRating() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radioBtn1) {
            return 1;
        } else if (selectedId == R.id.radioBtn2) {
            return 2;
        } else if (selectedId == R.id.radioBtn3) {
            return 3;
        } else if (selectedId == R.id.radioBtn4) {
            return 4;
        } else if (selectedId == R.id.radioBtn5) {
            return 5;
        } else {
            return 0;
        }
    }
}