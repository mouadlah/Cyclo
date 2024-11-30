package com.example.main_activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class UserActivity extends AppCompatActivity {

    private TextView JoinEvent;
    private TextView Rating;
    private ImageView Home;

    private String username; // Declare username at the class level so it can be accessed within onClickListeners

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.username = extras.getString("userName");
            String role = extras.getString("role");

            // Null checks added for the Log.d() calls
            if (username != null) {
                Log.d("AdminActivity", "Username: " + username);
            } else {
                Log.d("AdminActivity", "Username is null");
            }

            if (role != null) {
                Log.d("AdminActivity", "Role: " + role);
            } else {
                Log.d("AdminActivity", "Role is null");
            }

            TextView userNameView = findViewById(R.id.userNameId);
            TextView roleView = findViewById(R.id.roleId);
            userNameView.setText("UserName: " + (username != null ? username : "Unknown"));
            roleView.setText("Role: " + (role != null ? role : "Unknown"));
        }

        JoinEvent = findViewById(R.id.join);
        JoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, JoinEvent.class);
                if (username != null) {
                    intent.putExtra("userName", username); // Use the class-level 'username'
                }
                startActivity(intent);
            }
        });

        Rating = findViewById(R.id.rate);
        Rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, Feedback.class);
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
                Intent intent = new Intent(UserActivity.this, activity_front_page.class);
                startActivity(intent);
            }
        });
    }
}