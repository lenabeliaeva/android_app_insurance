package com.example.insurance.spinnerAdapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.ActivitySphere;

import java.util.List;

public class ActSpinnerAdapter extends ArrayAdapter<ActivitySphere> {
    Context context;
    List<ActivitySphere> values;

    public ActSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ActivitySphere> objects) {
        super(context, resource, objects);
        this.context = context;
        values = objects;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public ActivitySphere getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return values.get(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(values.get(position).getName());
        label.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(values.get(position).getName());
        return label;
    }
}
