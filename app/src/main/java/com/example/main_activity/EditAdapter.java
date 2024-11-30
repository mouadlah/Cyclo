package com.example.main_activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<event_data> list;
    private String username;

    public EditAdapter(Context context, ArrayList<event_data> list, String username) {
        this.context = context;
        this.list = list;
        this.username = username;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemedit, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        event_data event = list.get(position);
        holder.eventDate.setText(event.getDate());
        holder.type.setText(event.getTypeName());
        holder.capacity.setText(event.getCapacity());
        holder.location.setText(event.getLocation());

        holder.deleteImage.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && currentPosition < list.size()) {
                ((EditEvent) context).deleteEvent(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView deleteImage, editImage;
        TextView eventDate, type, capacity, location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteImage = itemView.findViewById(R.id.deleteImage);
            editImage = itemView.findViewById(R.id.editImage);
            eventDate = itemView.findViewById(R.id.eventDate);
            type = itemView.findViewById(R.id.type);
            capacity = itemView.findViewById(R.id.capacity);
            location = itemView.findViewById(R.id.location);

            editImage.setOnClickListener(v -> {
                int currentPosition = getAdapterPosition();
                if(currentPosition != RecyclerView.NO_POSITION && currentPosition < list.size()) {
                    event_data event = list.get(currentPosition);
                    Intent intent = new Intent(context, UpdateEvent.class);
                    intent.putExtra("eventKey", event.getDate().replace("/", "_"));
                    intent.putExtra("userName", username);
                    context.startActivity(intent);
                }
            });

            deleteImage.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < list.size()) {
                    ((EditEvent) context).deleteEvent(position);
                }
            });
        }
    }
}





