package com.example.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class club extends AppCompatActivity {

    private TextView EditProfile;
    private TextView AddEvent;
    private TextView EventManagement;
    private ImageView Home;

    private String username; // Declare username at the class level so it can be accessed within onClickListeners

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        // Retrieve 'extras' from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("userName"); // Assign to the class-level 'username' variable
            String role = extras.getString("role");

            // Log the values (with null checks)
            Log.d("ClubActivity", "Username: " + (username != null ? username : "null"));
            Log.d("ClubActivity", "Role: " + (role != null ? role : "null"));

            // Update UI with the retrieved values
            TextView userNameView = findViewById(R.id.userNameId);
            TextView roleView = findViewById(R.id.roleId);
            userNameView.setText("UserName: " + (username != null ? username : "Unknown"));
            roleView.setText("Role: " + (role != null ? role : "Unknown"));
        }

        EditProfile = findViewById(R.id.edit_profil);
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(club.this, club_profile.class);
                if (username != null) {
                    intent.putExtra("userName", username); // Use the class-level 'username'
                }
                startActivity(intent);
            }
        });

        AddEvent = findViewById(R.id.add_event);
        AddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(club.this, club_owner.class);
                if (username != null) {
                    intent.putExtra("userName", username); // Pass the username to the next activity
                }
                startActivity(intent);
            }
        });

        EventManagement = findViewById(R.id.event_management);
        EventManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(club.this, EditEvent.class);
                if (username != null) {
                    intent.putExtra("userName", username); // Pass the username to the next activity
                }
                startActivity(intent);
            }
        });

        Home = findViewById(R.id.house);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(club.this, activity_front_page.class);
                startActivity(intent);
            }
        });
    }
}
