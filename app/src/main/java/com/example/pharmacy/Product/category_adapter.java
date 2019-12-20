package com.example.pharmacy.Product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.R;

import java.util.ArrayList;

public class category_adapter extends RecyclerView.Adapter<category_adapter.MyViewHolder> {
    ArrayList<Category> Categories;
    public category_adapter(ArrayList<Category> Categories){
        this.Categories = Categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_row_item , parent , false);
        MyViewHolder myViewHolder  = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(Categories.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return this.Categories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.CategoryName);
        }
    }
}
