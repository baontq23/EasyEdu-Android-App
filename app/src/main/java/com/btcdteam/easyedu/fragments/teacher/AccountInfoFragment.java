package com.btcdteam.easyedu.fragments.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.btcdteam.easyedu.R;

public class AccountInfoFragment extends Fragment {
    EditText edName, edPhoneNumber, edOldPass, edNewPass, edRenewPass;
    Button btnChangePass, btnSave;
    ImageView btnBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edName = view.findViewById(R.id.ed_acc_info_name);
        edPhoneNumber = view.findViewById(R.id.ed_acc_info_phone_number);
        edOldPass = view.findViewById(R.id.ed_acc_info_old_pass);
        edNewPass = view.findViewById(R.id.ed_acc_info_new_pass);
        edRenewPass = view.findViewById(R.id.ed_acc_info_re_new_pass);
        btnChangePass = view.findViewById(R.id.btn_acc_info_change_pass);
        btnSave = view.findViewById(R.id.btn_acc_info_save);
        btnBack = view.findViewById(R.id.img_acc_info_back);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnChangePass.setOnClickListener(v -> {
            edOldPass.setVisibility(View.VISIBLE);
            edNewPass.setVisibility(View.VISIBLE);
            edRenewPass.setVisibility(View.VISIBLE);
        });
    }
}