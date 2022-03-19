package com.example.insurance.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService service;
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private final Retrofit mRetrofit;

    private NetworkService(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance(){
        if (service == null){
            service = new NetworkService();
        }
        return service;
    }

    public CategoriesApi getJSONApi(){
        return mRetrofit.create(CategoriesApi.class);
    }
}
