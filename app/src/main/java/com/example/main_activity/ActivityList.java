package com.example.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef;
    private Button Delete;
    private EditText DeleteActivity;

    // Method to be called in tests for setting mock DatabaseReference
    public void setTestDatabaseReference(DatabaseReference mockDatabaseReference) {
        this.mRef = mockDatabaseReference;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("EventTypes").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(getApplicationContext(), "Error getting data", Toast.LENGTH_LONG).show();
            } else {
                DataSnapshot dataSnapshot = task.getResult();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    myArrayList.add(userSnapshot.getKey());
                }
                ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myArrayList);
                myListView = findViewById(R.id.listview);
                myListView.setAdapter(myArrayAdapter);
            }
        });

        Delete = findViewById(R.id.delete);
        DeleteActivity = findViewById(R.id.DeleteActivity);

        Delete.setOnClickListener(view -> {
            String code = DeleteActivity.getText().toString().trim();
            if (!code.isEmpty()) {
                deleteUser(code);
            } else {
                Toast.makeText(ActivityList.this, "Please enter an Activity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void deleteUser(String code) {
        mRef.child("EventTypes").child(code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mRef.child("EventTypes").child(code).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ActivityList.this, "Activity deleted successfully", Toast.LENGTH_SHORT).show();
                            myArrayList.remove(code);
                            ((ArrayAdapter) myListView.getAdapter()).notifyDataSetChanged();
                        } else {
                            Toast.makeText(ActivityList.this, "Failed to delete Activity", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ActivityList.this, "Activity does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ActivityList.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
