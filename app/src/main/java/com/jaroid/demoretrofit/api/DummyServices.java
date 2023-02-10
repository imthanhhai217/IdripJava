package com.jaroid.demoretrofit.api;

import com.jaroid.demoretrofit.model.GetAllProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DummyServices {

    @GET("products")
    Call<GetAllProductsResponse> getAllProduct();

    @GET("products")
    Call<GetAllProductsResponse> getAllProduct(@Query("limit") int limit);
}
