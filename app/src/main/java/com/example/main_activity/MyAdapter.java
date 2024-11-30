package com.example.main_activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    private ImageView deleteImage;

    static ArrayList<activity_data> list;


    public MyAdapter(Context context, ArrayList<activity_data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        activity_data activityData = list.get(position);
        holder.typeName.setText(activityData.getTypeName());
        holder.age.setText(activityData.getAge());
        holder.level.setText(activityData.getLevel());
        holder.pace.setText(activityData.getPace());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }





        public static class MyViewHolder extends RecyclerView.ViewHolder{

            TextView typeName, level, age, pace;
            ImageView deleteImage; // Assuming you have a delete image in your item layout

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                level = itemView.findViewById(R.id.level);
                pace = itemView.findViewById(R.id.pace);
                age = itemView.findViewById(R.id.age);
                typeName = itemView.findViewById(R.id.activity);
                deleteImage = itemView.findViewById(R.id.deleteImage); // Make sure you have a corresponding ImageView in your layout

                deleteImage.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String key = list.get(position).getKey(); // Ensure you have a getKey method in activity_data
                        ((newlist) itemView.getContext()).deleteActivity(key);
                    }
                });
            }

    }

}
