package com.btcdteam.easyedu.fragments.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.btcdteam.easyedu.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CreateClassFragment extends Fragment {
    TextInputLayout inputLayoutClassName, inputLayoutSubject, inputLayoutDescription;
    TextInputEditText edClassName, edSubject, edDescription;
    Button btnCreateClass;
    ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_class, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputLayoutClassName = view.findViewById(R.id.ed_layout_create_class_class_name);
        inputLayoutSubject = view.findViewById(R.id.ed_layout_create_class_subject);
        inputLayoutDescription = view.findViewById(R.id.ed_layout_create_class_description);

        edClassName = view.findViewById(R.id.ed_create_class_class_name);
        edSubject = view.findViewById(R.id.ed_create_class_subject);
        edDescription = view.findViewById(R.id.ed_create_class_description);

        btnCreateClass = view.findViewById(R.id.btn_create_class_create);
        btnBack = view.findViewById(R.id.img_create_class_back);

        edClassName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()>0)
                btnCreateClass.setEnabled(true);
                else
                    btnCreateClass.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnCreateClass.setOnClickListener(v -> {

        });

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
}