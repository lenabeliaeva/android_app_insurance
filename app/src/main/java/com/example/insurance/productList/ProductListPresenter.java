package com.example.insurance.productList;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.Product;
import com.example.insurance.retrofit.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListPresenter {
    private final ProductListActivity activity;

    public ProductListPresenter(ProductListActivity activity) {
        this.activity = activity;
    }

    public void tryGetProductsByCategories(long categoryId) {
        NetworkService.getInstance()
                .getJSONProductApi()
                .getProductsByCategory(categoryId)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                        activity.setProductList(response.body());
                        activity.showProducts();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                        activity.showError();
                    }
                });
    }
}
