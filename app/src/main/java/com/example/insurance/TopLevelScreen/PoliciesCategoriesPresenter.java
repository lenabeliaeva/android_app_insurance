package com.example.insurance.TopLevelScreen;

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

    public void tryLogin() {
        NetworkService.getInstance()
                .getJSONApi()
                .getPoliciesCategoriesList()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        fragment.setCategories(response.body());
                        fragment.showCategories();
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        fragment.showError();
                    }
                });
    }
}
