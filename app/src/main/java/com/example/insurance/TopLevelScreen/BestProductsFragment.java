package com.example.insurance.TopLevelScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.insurance.R;
import com.example.insurance.pojo.Product;
import com.example.insurance.pojo.User;
import com.example.insurance.recyclerview.ProductCardListAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BestProductsFragment extends Fragment {

    private User user;
    private View view;
    private List<Product> bestProducts;

    public BestProductsFragment() {
        // Required empty public constructor
    }

    public BestProductsFragment(User u) {
        user = u;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_best_products, container, false);
        if (bestProducts == null) {
            ProductPresenter presenter = new ProductPresenter(this);
            if (user.isConnected()) {
                presenter.tryGetByHybrid(user.getId());
            } else {
                presenter.tryGetProductsByBayesAvg();
            }
        } else {
            showBestProducts();
        }
        return view;
    }

    public void setBestProducts(List<Product> response) {
        bestProducts = response;
    }

    public void showError() {
        Snackbar.make(view, "Попробуйте позже", Snackbar.LENGTH_LONG).show();
    }

    public void showBestProducts() {
        RecyclerView rv = view.findViewById(R.id.rv_best_products);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        rv.setAdapter(new ProductCardListAdapter(bestProducts));
    }
}