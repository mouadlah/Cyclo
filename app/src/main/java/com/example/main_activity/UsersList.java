package com.example.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {

    private Button Delete;
    private EditText DeleteUsername;

    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    DatabaseReference mRef;

    public UsersList() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("user").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
                Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            } else {
                DataSnapshot dataSnapshot = task.getResult();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    myArrayList.add(userSnapshot.getKey());
                    Log.e("firebase", "User: " + userSnapshot.getKey());
                }
                ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myArrayList);
                myListView = findViewById(R.id.listview);
                myListView.setAdapter(myArrayAdapter);
            }
        });

        Delete = findViewById(R.id.delete);
        DeleteUsername = findViewById(R.id.DeleteActivity);

        Delete.setOnClickListener(view -> {
            String username = DeleteUsername.getText().toString().trim();
            if (!username.isEmpty()) {
                checkAndDeleteUser(username);
            } else {
                Toast.makeText(UsersList.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAndDeleteUser(String username) {
        mRef.child("user").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    deleteUser(username);
                } else {
                    Toast.makeText(UsersList.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UsersList.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteUser(String username) {
        mRef.child("user").child(username).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(UsersList.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                myArrayList.remove(username);
                ((ArrayAdapter) myListView.getAdapter()).notifyDataSetChanged();
            } else {
                Toast.makeText(UsersList.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
