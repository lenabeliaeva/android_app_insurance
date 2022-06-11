package com.example.insurance;

import androidx.annotation.NonNull;

import com.example.insurance.pojo.User;
import com.example.insurance.retrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter {
    private final SignUpActivity activity;

    SignUpPresenter(SignUpActivity a) {
        activity = a;
    }

    public void tryToSignUp(User user) {
        NetworkService.getInstance()
                .getJSONUserApi()
                .signUp(user)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        activity.doAfterSuccess(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        activity.doAfterFailure();
                    }
                });
    }
}
