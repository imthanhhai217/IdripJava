package com.jaroid.demoretrofit.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.jaroid.demoretrofit.R;
import com.jaroid.demoretrofit.adapters.ProductAdapter;
import com.jaroid.demoretrofit.api.DummyServices;
import com.jaroid.demoretrofit.databases.SqlHelper;
import com.jaroid.demoretrofit.interfaces.WishListChangeListener;
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
    private ArrayList<Integer> mWishList;
    private DummyServices mDummyServices;

    private SqlHelper sqlHelper;

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
        sqlHelper = new SqlHelper(getContext());
        sqlHelper.getReadableDatabase();
        mWishList = sqlHelper.getAllWishId();

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

    private boolean checkWishList(int id) {
        return mWishList.contains(id);
    }

    private void fetchHotDeals(List<Product> data) {
        mHotDeals.clear();
        mHotDeals = (ArrayList<Product>) data
                .stream()
                .filter(p -> p.getDiscountPercentage() >= 10.0
                ).collect(Collectors.toList());

        Log.d(TAG, "fetchHotDeals: " + mHotDeals.size());

        for (int i = 0; i < mHotDeals.size(); i++) {
            if (checkWishList(mHotDeals.get(i).getId())) {
                Product product = mHotDeals.get(i);
                product.setWishList(true);
                mHotDeals.set(i, product);
            }
        }

        mHotDealAdapter.setData(mHotDeals);

        Product saveProduct = mHotDeals.get(0);

        Gson gson = new Gson();
        String dataProduct = gson.toJson(saveProduct);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("DATA_PRODUCT", dataProduct).commit();
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
        mHotDealAdapter.setWishListChangeListener(hotDealWishListChangeListener);
        mMostPopularAdapter = new ProductAdapter(mMostPopular);
        mMostPopularAdapter.setWishListChangeListener(mostPopularWishListChangeListener);
    }

    private WishListChangeListener hotDealWishListChangeListener = new WishListChangeListener() {
        @Override
        public void onAddWishList(int position) {
            Product product = mHotDeals.get(position);
            product.setWishList(true);
            mHotDeals.set(position, product);
            mHotDealAdapter.notifyItemChanged(position);

            sqlHelper.addWish(product.getId(), product.getTitle(), product.getPrice() + "");
        }

        @Override
        public void onRemoveWishList(int position) {
            Product product = mHotDeals.get(position);
            product.setWishList(false);
            mHotDeals.set(position, product);
            mHotDealAdapter.notifyItemChanged(position);
            sqlHelper.removeWish(product.getId());
        }
    };

    private WishListChangeListener mostPopularWishListChangeListener = new WishListChangeListener() {
        @Override
        public void onAddWishList(int id) {

        }

        @Override
        public void onRemoveWishList(int id) {

        }
    };
}