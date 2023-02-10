package com.jaroid.demoretrofit.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instances = null;
    private static final String BASE_URL = "https://dummyjson.com/";

    public static Retrofit getInstances() {
        if (instances == null) {
            instances = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instances;
    }
}
