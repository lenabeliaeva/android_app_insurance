package com.example.insurance.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insurance.ProductListActivity;
import com.example.insurance.R;
import com.example.insurance.pojo.Category;

import java.util.List;

public class CustomListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final List<Category> categories;

    public CustomListAdapter(List<Category> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_tv_row, parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        final Category category = categories.get(position);
        holder.getTextView().setText(category.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_POLICE_ID, category.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
