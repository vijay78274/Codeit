package com.example.codeit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codeit.R;

import java.util.List;

public class MainContentAdapter extends RecyclerView.Adapter<MainContentAdapter.ViewHolder> {
    private List<String> itemList;
    Context context;
    public MainContentAdapter(Context context, List<String> itemList) {
        this.context=context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public MainContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_view, parent, false);
        return new MainContentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainContentAdapter.ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.itemTitle.setText(item);
        holder.count.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.steps);
            count = itemView.findViewById(R.id.count);
        }
    }
}
