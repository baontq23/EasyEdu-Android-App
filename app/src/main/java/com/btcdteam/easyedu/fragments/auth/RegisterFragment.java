package com.btcdteam.easyedu.fragments.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.btcdteam.easyedu.R;

public class RegisterFragment extends Fragment {
    ImageButton btnRegisterBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRegisterBack = view.findViewById(R.id.btn_register_back);

        btnRegisterBack.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_auth).navigate(R.id.action_registerFragment_to_chooseActionFragment);
        });
    }
}