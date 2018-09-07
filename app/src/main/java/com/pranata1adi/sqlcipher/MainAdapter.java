package com.pranata1adi.sqlcipher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    ArrayList<Item> itemArrayList;
    Context context;

    public MainAdapter(ArrayList<Item> itemArrayList, Context context) {
        this.itemArrayList = itemArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final Item item = itemArrayList.get(position);
        holder.MainText.setText(item.getName());

        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEditActivity.class);
                intent.putExtra("ITEM", item);
                context.startActivity(intent);
            }
        });

        holder.doneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabase database = new MyDatabase(context);
                database.deleteItem(item.getId());
                itemArrayList.clear();
                itemArrayList.addAll(database.getAll());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView MainText;
        ImageView editImageView;
        ImageView doneImageView;

        public MainViewHolder(View itemView) {
            super(itemView);
            MainText = itemView.findViewById(R.id.todo_name);
            editImageView = itemView.findViewById(R.id.edit_todo);
            doneImageView = itemView.findViewById(R.id.done_todo);
        }
    }
}