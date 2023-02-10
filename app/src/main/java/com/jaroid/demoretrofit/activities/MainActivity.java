package com.jaroid.demoretrofit.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jaroid.demoretrofit.R;
import com.jaroid.demoretrofit.fragments.AccountFragment;
import com.jaroid.demoretrofit.fragments.CartFragment;
import com.jaroid.demoretrofit.fragments.CategoryFragment;
import com.jaroid.demoretrofit.fragments.HomeFragment;
import com.jaroid.demoretrofit.fragments.WishListFragment;
import com.jaroid.demoretrofit.model.GetAllProductsResponse;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private BottomNavigationView bnvMain;
    private Fragment mFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

//        dummyServices.getAllProduct(0).enqueue(new Callback<GetAllProductsResponse>() {
//            @Override
//            public void onResponse(Call<GetAllProductsResponse> call, Response<GetAllProductsResponse> response) {
//                if (response.isSuccessful()) {
//                    if (response.code() == 200) {
//                        Log.d(TAG, "onResponse: all : " + response.body().getProducts().size());
//                        GetAllProductsResponse getAllProductsResponse = response.body();
//                        List<Product> listProducts = getAllProductsResponse.getProducts();
//                        Log.d(TAG, "onResponse: " + listProducts.get(0).toString());
//                    }
//                    if (response.code() == 400) {
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAllProductsResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
    }

    private void initData() {


    }

    private void updateProducts(Response<GetAllProductsResponse> response) {

    }

    private void initView() {

        bnvMain = findViewById(R.id.bnvMain);
        bnvMain.setOnItemSelectedListener(this::onNavigationItemSelected);

        mFragment = HomeFragment.newInstance();
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home");
        }
        loadFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
            case R.id.navigationHome:
                Log.d(TAG, "onNavigationItemSelected: navigationHome");
                mFragment = null;
                mFragment = HomeFragment.newInstance();
                if (actionBar != null) {
                    actionBar.setTitle("Home");
                }
                loadFragment();
                return true;
            case R.id.navigationWishList:
                Log.d(TAG, "onNavigationItemSelected: navigationWishList");
                mFragment = null;
                mFragment = WishListFragment.newInstance();
                if (actionBar != null) {
                    actionBar.setTitle("Wish list");
                }
                loadFragment();
                return true;
            case R.id.navigationCategory:
                Log.d(TAG, "onNavigationItemSelected: navigationCategory");
                mFragment = null;
                mFragment = CategoryFragment.newInstance();
                if (actionBar != null) {
                    actionBar.setTitle("Category");
                }
                loadFragment();
                return true;
            case R.id.navigationAccount:
                Log.d(TAG, "onNavigationItemSelected: navigationAccount");
                mFragment = null;
                mFragment = AccountFragment.newInstance();
                if (actionBar != null) {
                    actionBar.setTitle("Account");
                }
                loadFragment();
                return true;
            case R.id.navigationCart:
                Log.d(TAG, "onNavigationItemSelected: navigationCart");
                mFragment = null;
                mFragment = CartFragment.newInstance();
                if (actionBar != null) {
                    actionBar.setTitle("Cart");
                }
                loadFragment();
                return true;
        }
    }

    private void loadFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, mFragment)
                .addToBackStack(null)
                .commit();
    }
}