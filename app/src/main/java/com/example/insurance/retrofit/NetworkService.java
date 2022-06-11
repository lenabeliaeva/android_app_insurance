package com.example.insurance.retrofit;

import com.example.insurance.pojo.Police;

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

    public CategoriesApi getJSONCategoriesApi() {
        return mRetrofit.create(CategoriesApi.class);
    }

    public ProductApi getJSONProductApi() {
        return mRetrofit.create(ProductApi.class);
    }

    public PoliceApi getJSONPoliceApi() {
        return mRetrofit.create(PoliceApi.class);
    }

    public UserApi getJSONUserApi() {
        return mRetrofit.create(UserApi.class);
    }

    public CarApi getJSONCarApi() {
        return mRetrofit.create(CarApi.class);
    }

    public RatingApi getJSONRatingApi() {
        return mRetrofit.create(RatingApi.class);
    }
}
