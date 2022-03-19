package com.example.insurance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    int[] id;
    String[] names;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int[] id, String[] names){
        this.context = context;
        this.id = id;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return id[position];
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return inflater.inflate(R.layout.activity_order_police, parent);
    }
}
