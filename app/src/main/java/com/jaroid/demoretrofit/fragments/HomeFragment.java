package com.jaroid.demoretrofit.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jaroid.demoretrofit.R;
import com.jaroid.demoretrofit.adapters.ProductAdapter;
import com.jaroid.demoretrofit.api.DummyServices;
import com.jaroid.demoretrofit.model.GetAllProductsResponse;
import com.jaroid.demoretrofit.model.Product;
import com.jaroid.demoretrofit.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.tvSeeAllHotDeals)
    TextView tvSeeAllHotDeals;
    @BindView(R.id.tvSeeAllMostPopular)
    TextView tvSeeAllMostPopular;
    @BindView(R.id.rvHotDeals)
    RecyclerView rvHotDeals;
    @BindView(R.id.rvMostPopular)
    RecyclerView rvMostPopular;

    private ProductAdapter mHotDealAdapter;
    private ProductAdapter mMostPopularAdapter;
    private ArrayList<Product> mHotDeals;
    private ArrayList<Product> mMostPopular;
    private DummyServices mDummyServices;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initData() {
        mDummyServices = RetrofitClient.getInstances().create(DummyServices.class);
        mDummyServices.getAllProduct(0).enqueue(new Callback<GetAllProductsResponse>() {
            @Override
            public void onResponse(Call<GetAllProductsResponse> call, Response<GetAllProductsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        GetAllProductsResponse model = response.body();
                        List<Product> data = model.getProducts();
                        if (!data.isEmpty() && data.size() > 0) {
                            fetchHotDeals(data);
                            fetchMostPopular(data);
                        }
                    } else {
                        Log.d(TAG, "onResponse: Error code : " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllProductsResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void fetchMostPopular(List<Product> data) {
        mMostPopular.clear();
        mMostPopular = (ArrayList<Product>) data
                .stream()
                .filter(p -> p.getRating() >= 4.5)
                .collect(Collectors.toList());

        Log.d(TAG, "fetchMostPopular: " + mMostPopular.size());
        mMostPopularAdapter.setData(mMostPopular);
    }

    private void fetchHotDeals(List<Product> data) {
        mHotDeals.clear();
        mHotDeals = (ArrayList<Product>) data
                .stream()
                .filter(p -> p.getDiscountPercentage() >= 15.0
                ).collect(Collectors.toList());

        Log.d(TAG, "fetchHotDeals: " + mHotDeals.size());
        mHotDealAdapter.setData(mHotDeals);
    }


    private void initView(@NonNull View view) {
        ButterKnife.bind(this, view);

        initAdapter();

        rvHotDeals.setAdapter(mHotDealAdapter);
        rvMostPopular.setAdapter(mMostPopularAdapter);
    }

    private void initAdapter() {
        mHotDeals = new ArrayList<>();
        mMostPopular = new ArrayList<>();
        mHotDealAdapter = new ProductAdapter(mHotDeals);
        mMostPopularAdapter = new ProductAdapter(mMostPopular);
    }
}