package com.jaroid.demoretrofit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jaroid.demoretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.tvDemoAnimation)
    TextView tvDemoAnimation;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {

        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDemoAnimation.setVisibility(View.GONE);
                runAnimation(v1,R.anim.fade);
            }
        });

        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDemoAnimation.setVisibility(View.VISIBLE);
                runAnimation(v1,R.anim.to_right);
            }
        });
    }

    private void runAnimation(View view, int id) {
        Animation animation = AnimationUtils.loadAnimation(getContext(),id);
        view.startAnimation(animation);
    }
}