package com.example.insurance.TopLevelScreen;

import androidx.annotation.NonNull;

import com.example.insurance.retrofit.NetworkService;
import com.example.insurance.pojo.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliciesCategoriesPresenter {
    private final PoliciesCategoryFragment fragment;

    public PoliciesCategoriesPresenter(PoliciesCategoryFragment fragment) {
        this.fragment = fragment;
    }

    public void tryGetCategories() {
        NetworkService.getInstance()
                .getJSONApi()
                .getPoliciesCategoriesList()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
                        fragment.setCategories(response.body());
                        fragment.showCategories();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable t) {
                        fragment.showError();
                    }
                });
    }
}
