package com.example.codeit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codeit.Model.ContentModel;


import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private List<ContentModel> itemList;
    Context context;
    public ContentAdapter(Context context, List<ContentModel> itemList) {
        this.context=context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_contents, parent, false);
        return new ContentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentModel item = itemList.get(position);
        holder.count.setText(item.getCount());
        holder.title.setText(item.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainContent.class);
                intent.putExtra("course",item.getCourse());
                intent.putExtra("title",item.getTitle());
                intent.putExtra("count",item.getCount());
                context.startActivity(intent);
            }
        });
        if(item.isStatus()){
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setImageResource(R.drawable.correct);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView count;
        TextView title;
        ImageView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.count);
            title = itemView.findViewById(R.id.title);
            status = itemView.findViewById(R.id.status);
        }
    }
}
