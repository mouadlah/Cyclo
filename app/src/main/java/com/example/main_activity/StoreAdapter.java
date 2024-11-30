package com.example.main_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {

    private final Context context;
    private final String username; // Assuming this is the username of the logged-in user
    private final ArrayList<activity_data> list;
    private final String eventKey; // This is the date key of the event

    public StoreAdapter(Context context, ArrayList<activity_data> list, String username, String eventKey) {
        this.context = context;
        this.list = list;
        this.username = username;
        this.eventKey = eventKey;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        activity_data activityData = list.get(position);
        holder.bind(activityData);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView activity, level, age, pace;
        ImageView storeImage; // ImageView for the store button

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activity = itemView.findViewById(R.id.activity);
            level = itemView.findViewById(R.id.level);
            age = itemView.findViewById(R.id.age);
            pace = itemView.findViewById(R.id.pace);
            storeImage = itemView.findViewById(R.id.storeImage);

            storeImage.setOnClickListener(v -> storeActivity());
        }

        public void bind(activity_data activityData) {
            activity.setText(activityData.getTypeName());
            age.setText(activityData.getAge());
            level.setText(activityData.getLevel());
            pace.setText(activityData.getPace());
        }

        private void storeActivity() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                activity_data selectedActivity = list.get(position);

                if (username == null || eventKey == null) {
                    Toast.makeText(context, "Error: Username or Event Key is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");
                DatabaseReference eventRef = userRef.child(username).child("events").child(eventKey);

                // Creating a map of the activity data to be added
                Map<String, Object> activityValues = new HashMap<>();
                activityValues.put("age", selectedActivity.getAge());
                activityValues.put("level", selectedActivity.getLevel());
                activityValues.put("pace", selectedActivity.getPace());
                activityValues.put("typeName", selectedActivity.getTypeName());

                // Merging with existing data
                eventRef.updateChildren(activityValues)
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Activity data added successfully", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(context, "Failed to add activity data", Toast.LENGTH_SHORT).show());
            }
        }

    }
}
