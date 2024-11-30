package com.example.main_activity;
import android.content.Intent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private final ArrayList<Event> list;
    private final String username;

    public EventAdapter(Context context, ArrayList<Event> list, String username) {
        this.context = context;
        this.list = list;
        this.username = username;
    }
    public void filterList(ArrayList<Event> filteredList) {
        list.clear(); // Clear the current list
        list.addAll(filteredList); // Add all the items from the filtered list
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemjoin, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = list.get(position);
        holder.name.setText(event.getName());
        holder.type.setText(event.getTypeName());
        holder.club.setText(event.getClubName());

        holder.arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EventForm activity
                Intent intent = new Intent(context, EventForm.class);
                intent.putExtra("userName", username);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, club;
        ImageView arrowImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            club = itemView.findViewById(R.id.club);
            arrowImage = itemView.findViewById(R.id.arrowImage);
        }
    }
}
