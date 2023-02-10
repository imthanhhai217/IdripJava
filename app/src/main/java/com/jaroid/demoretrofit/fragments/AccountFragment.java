package com.jaroid.demoretrofit.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jaroid.demoretrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.edtUser)
    EditText edtUser;
    @BindView(R.id.btnSave)
    Button btnSave;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    private void initView(View view) {
        ButterKnife.bind(this, view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_DATA",Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("USER_NAME","null");
        edtUser.setHint(userName);

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                saveUser();
                break;
        }

    }

    private void saveUser() {
        String userName = edtUser.getText().toString().trim();

        SharedPreferences mySharef = getActivity()
                .getSharedPreferences("USER_DATA", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = mySharef.edit();
        editor.putString("USER_NAME", userName);
        editor.commit();
    }
}