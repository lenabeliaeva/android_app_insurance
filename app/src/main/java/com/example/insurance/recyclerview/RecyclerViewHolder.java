package com.example.insurance.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insurance.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final TextView tv;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.tv_pol_categ_name);
    }

    public TextView getTextView() {
        return tv;
    }
}
