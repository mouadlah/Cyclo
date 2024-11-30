package com.example.main_activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class JoinEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private DatabaseReference databaseRef;
    public ArrayList<Event> eventList = new ArrayList<>(); // Initialized
    private ArrayList<Event> filteredEventList = new ArrayList<>(); // Added this line
    public EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);

        String username = getIntent().getStringExtra("userName");

        recyclerView = findViewById(R.id.join);
        searchView = findViewById(R.id.search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(this, eventList, username);
        recyclerView.setAdapter(eventAdapter);
        setupSearchView();

        databaseRef = FirebaseDatabase.getInstance().getReference("user");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String role = userSnapshot.child("role").getValue(String.class);
                    if ("Cycling Club".equals(role)) {
                        String clubName = userSnapshot.child("club profile").child("clubName").getValue(String.class);
                        for (DataSnapshot eventSnapshot : userSnapshot.child("events").getChildren()) {
                            Event event = eventSnapshot.getValue(Event.class);
                            if (event != null) {
                                event.setClubName(clubName);
                                eventList.add(event);
                            }
                        }
                    }
                }
                eventAdapter.notifyDataSetChanged();

                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            closeKeyboard();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JoinEvent.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    public void filter(String text) {
        if (text.isEmpty()) {
            eventAdapter.filterList(new ArrayList<>(eventList)); // Reset to the original list of events
        } else {
            ArrayList<Event> filteredList = new ArrayList<>();
            for (Event item : eventList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getTypeName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getClubName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            eventAdapter.filterList(filteredList); // Apply the filter
        }
    }

}


