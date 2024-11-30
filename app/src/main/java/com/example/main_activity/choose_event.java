package com.example.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class choose_event extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    StoreAdapter storeAdapter;
    ArrayList<activity_data> list;
    String eventKey;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_event);

        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("EventTypes");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        eventKey = getIntent().getStringExtra("date");
        username = getIntent().getStringExtra("userName"); // Assume you pass "userName" along with "eventKey"

        if (username == null || eventKey == null) {
            Toast.makeText(this, "Error: Username or Event Key is null", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity if critical information is missing
            return;
        }

        storeAdapter = new StoreAdapter(this, list, username, eventKey);
        recyclerView.setAdapter(storeAdapter);

        loadDataFromFirebase(); // Call the method that handles Firebase data loading
    }

    public void loadDataFromFirebase() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    activity_data activityData = dataSnapshot.getValue(activity_data.class);
                    if (activityData != null) {
                        activityData.setKey(dataSnapshot.getKey());
                        list.add(activityData);
                    }
                }
                storeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(choose_event.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
