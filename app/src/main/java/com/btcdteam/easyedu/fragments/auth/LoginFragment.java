package com.btcdteam.easyedu.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.btcdteam.easyedu.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {
    TextInputLayout inputLayoutLoginPhoneNumber, inputLayoutLoginPassword;
    TextInputEditText edLoginPhoneNumber, edLoginPassword;
    Button btnLogin, btnLoginGoogle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputLayoutLoginPhoneNumber = view.findViewById(R.id.ed_layout_login_phone_number);
        inputLayoutLoginPassword = view.findViewById(R.id.ed_layout_login_password);
        edLoginPhoneNumber = view.findViewById(R.id.ed_login_phone_number);
        edLoginPassword = view.findViewById(R.id.ed_login_password);
        btnLogin = view.findViewById(R.id.btn_login_login);
        btnLoginGoogle = view.findViewById(R.id.btn_login_login_google);

        btnLogin.setOnClickListener(v -> {

        });

        btnLoginGoogle.setOnClickListener(v -> {

        });
    }
}