package com.example.insurance.TopLevelScreen;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.Product;
import com.example.insurance.retrofit.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter {
    private final BestProductsFragment fragment;

    public ProductPresenter(BestProductsFragment fragment) {
        this.fragment = fragment;
    }

    public void tryGetProductsByBAyesAvg() {
        NetworkService.getInstance()
                .getJSONProductApi()
                .getBayesAvgProducts()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                        fragment.setBestProducts(response.body());
                        fragment.showBestProducts();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                        fragment.showError();
                    }
                });
    }
}
