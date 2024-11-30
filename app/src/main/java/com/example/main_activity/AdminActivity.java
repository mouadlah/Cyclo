package com.example.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.main_activity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends AppCompatActivity {

    private TextView AccountManagement;
    private TextView EventManagement;
    private TextView ContentModeration;
    private ImageView Home;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView welcome;
    String post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("userName");
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
        AccountManagement = (TextView) findViewById(R.id.AccountManagement);
        AccountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(AdminActivity.this,UsersList.class);
                    startActivity(intent);
            }
        });

        EventManagement = (TextView) findViewById(R.id.EventManagement);
        EventManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,ManagementEvent.class);
                startActivity(intent);
            }
        });

        ContentModeration = (TextView) findViewById(R.id.ContentModeration);
        ContentModeration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,newlist.class);
                startActivity(intent);
            }
        });

        Home = (ImageView) findViewById(R.id.house);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this,activity_front_page.class);
                startActivity(intent);
            }
        });
    }

}