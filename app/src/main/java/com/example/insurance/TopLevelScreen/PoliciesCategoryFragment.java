package com.example.insurance.TopLevelScreen;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.insurance.R;
import com.example.insurance.pojo.Category;
import com.example.insurance.recyclerview.CustomListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PoliciesCategoryFragment extends Fragment {
    private View view;
    private List<Category> categories;
    private PoliciesCategoriesPresenter presenter;

    public PoliciesCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_policies_categories, container, false);

        presenter = new PoliciesCategoriesPresenter(this);

        getCategoriesList();

        return view;
    }

    public void setCategories(List<Category> response) {
        categories = response;
    }

    public void showError() {
        Snackbar.make(view, "Попробуйте позже", Snackbar.LENGTH_LONG).show();
    }

    private void getCategoriesList() {
        presenter.tryGetCategories();
    }

    public void showCategories() {
        if (getActivity() != null) {
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_drawable);
            if (drawable != null) {
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
                dividerItemDecoration.setDrawable(drawable);
                RecyclerView rv = view.findViewById(R.id.rv_pol_categ);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                rv.setAdapter(new CustomListAdapter(categories));
                rv.addItemDecoration(dividerItemDecoration);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
