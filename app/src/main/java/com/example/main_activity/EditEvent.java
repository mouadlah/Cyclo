package com.example.main_activity;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.widget.Toast;

public class EditEvent extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    EditAdapter editAdapter;
    ArrayList<event_data> list;
    private String username;
    private boolean isDeleting = false; // Flag to indicate deletion in progress

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        recyclerView = findViewById(R.id.userList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        username = getIntent().getStringExtra("userName");
        database = FirebaseDatabase.getInstance().getReference("user").child(username).child("events");
        list = new ArrayList<>();
        editAdapter = new EditAdapter(this, list, username);
        recyclerView.setAdapter(editAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isDeleting) { // Check if deletion is in progress
                    Log.d("EditEvent", "onDataChange triggered, list size before clear: " + list.size());
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        event_data event = dataSnapshot.getValue(event_data.class);
                        if (event != null) {
                            event.setDate(dataSnapshot.getKey().replace("_", "/"));
                            list.add(event);
                        }
                    }
                    editAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditEvent.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteEvent(int position) {
        Log.d("EditEvent", "Attempting to delete at position: " + position + " list size before deletion: " + list.size());
        if (position < 0 || position >= list.size()) {
            Toast.makeText(this, "Invalid position", Toast.LENGTH_SHORT).show();
            return;
        }

        event_data eventToDelete = list.get(position);
        String key = eventToDelete.getDate().replace("/", "_");
        DatabaseReference mRef = database.child(key);

        isDeleting = true; // Set flag to indicate deletion is in progress

        mRef.removeValue().addOnCompleteListener(task -> {
            isDeleting = false; // Reset flag after deletion attempt
            if (task.isSuccessful()) {
                if (position < list.size()) {
                    list.remove(position);
                    editAdapter.notifyDataSetChanged();
                    Toast.makeText(EditEvent.this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EditEvent.this, "Failed to delete event", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // ... rest of the EditEvent class if any
}
