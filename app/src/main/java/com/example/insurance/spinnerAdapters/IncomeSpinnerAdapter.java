package com.example.insurance.spinnerAdapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.IncomeLevel;

import java.util.List;

public class IncomeSpinnerAdapter extends ArrayAdapter<IncomeLevel> {
    Context context;
    List<IncomeLevel> values;

    public IncomeSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<IncomeLevel> objects) {
        super(context, resource, objects);
        this.context = context;
        values = objects;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public IncomeLevel getItem(int position) {
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
