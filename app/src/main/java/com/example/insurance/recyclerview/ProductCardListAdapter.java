package com.example.insurance.recyclerview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insurance.FillingPoliceActivity;
import com.example.insurance.R;
import com.example.insurance.pojo.Product;

import java.util.List;

public class ProductCardListAdapter extends RecyclerView.Adapter<CardListRVHolder> {
    private final List<Product> products;

    public ProductCardListAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public CardListRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_card_list_item, parent,false);
        return new CardListRVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardListRVHolder holder, int position) {
        final Product product = products.get(position);
        holder.getTvName().setText(product.getName());
        holder.getRatingBar().setRating(product.getBayesAverageRating());
        holder.getImage().setImageResource(R.drawable.common_product);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FillingPoliceActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
