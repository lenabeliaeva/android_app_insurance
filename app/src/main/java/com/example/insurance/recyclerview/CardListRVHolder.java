package com.example.insurance.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insurance.R;

public class CardListRVHolder extends RecyclerView.ViewHolder {
    private final TextView tvName;
    private final RatingBar ratingBar;
    private final ImageView image;

    public CardListRVHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_product_name);
        ratingBar = itemView.findViewById(R.id.product_rating);
        image = itemView.findViewById(R.id.product_img);
    }

    public TextView getTvName() {
        return tvName;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public ImageView getImage() {
        return image;
    }
}
