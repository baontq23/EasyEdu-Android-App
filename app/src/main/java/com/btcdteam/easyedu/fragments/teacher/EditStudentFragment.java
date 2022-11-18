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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.btcdteam.easyedu.R;

public class EditStudentFragment extends Fragment implements TextWatcher {
    EditText edStudentName, edStudentDOB,
            edRegularScore1Term1, edRegularScore2Term1, edRegularScore3Term1, edMidtermScoreTerm1, edFinalScoreTerm1,
            edRegularScore1Term2, edRegularScore2Term2, edRegularScore3Term2, edMidtermScoreTerm2, edFinalScoreTerm2,
            edParentPhoneNumber, edParentName, edParentDOB, edParentEmail;
    Spinner spStudentGender;
    String[] items = new String[]{"Nam", "Nữ"};
    ImageView btnBack;
    Button btnSave, btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //điều hướng
        btnBack = view.findViewById(R.id.img_edit_student_back);
        btnSave = view.findViewById(R.id.btn_edit_student_save);
        btnSearch = view.findViewById(R.id.btn_parent_search_phone_number);
        // tên, ngày sinh
        edStudentName = view.findViewById(R.id.ed_student_name);
        edStudentDOB = view.findViewById(R.id.ed_student_dob);
        //điểm kỳ 1
        edRegularScore1Term1 = view.findViewById(R.id.ed_regular_1_term_1);
        edRegularScore2Term1 = view.findViewById(R.id.ed_regular_2_term_1);
        edRegularScore3Term1 = view.findViewById(R.id.ed_regular_3_term_1);
        edMidtermScoreTerm1 = view.findViewById(R.id.ed_midterm_term_1);
        edFinalScoreTerm1 = view.findViewById(R.id.ed_final_term_1);
        //điểm kỳ 2
        edRegularScore1Term2 = view.findViewById(R.id.ed_regular_1_term_2);
        edRegularScore2Term2 = view.findViewById(R.id.ed_regular_2_term_2);
        edRegularScore3Term2 = view.findViewById(R.id.ed_regular_3_term_2);
        edMidtermScoreTerm2 = view.findViewById(R.id.ed_midterm_term_2);
        edFinalScoreTerm2 = view.findViewById(R.id.ed_final_term_2);
        //giới tính
        spStudentGender = view.findViewById(R.id.sp_student_gender);
        //Phụ huynh
        edParentPhoneNumber = view.findViewById(R.id.ed_parent_phone_number);
        edParentName = view.findViewById(R.id.ed_parent_name);
        edParentDOB = view.findViewById(R.id.ed_parent_dob);
        edParentEmail = view.findViewById(R.id.ed_parent_email);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spStudentGender.setAdapter(adapter);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        // set ban phim khong che Edittext
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //check null
        edStudentName.addTextChangedListener(this);
        edStudentDOB.addTextChangedListener(this);

        btnSave.setOnClickListener(v -> {
            //lưu học sinh
        });

        btnSearch.setOnClickListener(v -> {
            // Tìm số điện thoại phụ huynh,
            // không tìm thấy thì set enable các edt bị disable thành true để người dùng nhập
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (edStudentDOB.getText().toString().trim().length() > 0 && edStudentName.getText().toString().trim().length() > 0) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}