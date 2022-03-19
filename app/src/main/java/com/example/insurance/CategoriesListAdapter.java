package com.example.insurance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.insurance.pojo.Category;

import java.util.List;

public class CategoriesListAdapter extends ArrayAdapter<Category> {

    private int resourceLayout;
    private Context mContext;

    public CategoriesListAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            view = vi.inflate(resourceLayout, null);
        }
        Category category = getItem(position);
        if (category != null){
            TextView tv = view.findViewById(R.id.tv_pol_categ_name);
            if (tv != null){
                tv.setText(category.getName());
            }
        }
        return view;
    }
}
