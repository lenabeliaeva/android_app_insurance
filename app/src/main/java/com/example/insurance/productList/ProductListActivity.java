package com.example.insurance.productList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;

import com.example.insurance.FillingPoliceActivity;
import com.example.insurance.ProductListAdapter;
import com.example.insurance.pojo.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends ListActivity {
    private List<Product> products;
    public static final String EXTRA_CATEGORY_ID = "categoryId";
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductListPresenter presenter = new ProductListPresenter(this);
        int categoryId = (int) getIntent().getExtras().get(EXTRA_CATEGORY_ID);
        presenter.tryGetProductsByCategories(categoryId);
        //user = (User) getIntent().getExtras().get(EXTRA_USER);

    }

    public void setProductList(List<Product> products) {
        this.products = products;
    }

    public void showProducts() {
        AdapterView.OnItemClickListener itemClickListener = (parent, view, position, id) -> {
            Intent intent = new Intent(ProductListActivity.this, FillingPoliceActivity.class);
            intent.putExtra(FillingPoliceActivity.EXTRA_CATEGORY_ID, products.get(position).getId());
//            intent.putExtra(FillingPoliceActivity.EXTRA_USER, user);
            startActivity(intent);
        };
        getListView().setOnItemClickListener(itemClickListener);
        setListAdapter(new ProductListAdapter(ProductListActivity.this, products));
    }

    public void showError() {
//        Snackbar.make(this.getCurrentFocus(), "Попробуйте позже", Snackbar.LENGTH_LONG).show();
    }
}
