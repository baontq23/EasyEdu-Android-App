package com.btcdteam.easyedu.fragments.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.btcdteam.easyedu.R;

public class StudentDetailsFragment extends Fragment {
    private ImageView btnBack, btnSendFeedback;
    private TextView tvStudentName,
            tvStudentDateOfBirth,
            tvStudentGender,
            tvRegularScore1,
            tvRegularScore2,
            tvRegularScore3,
            tvMidtermScore,
            tvFinalScore,
            tvTotalScore,
            tvParentName,
            tvParentDateOfBirth,
            tvEmail,
            tvPhoneNumber;
    private EditText edSendFeedBack;
    private Switch swTerm;
    Button btnDeleteStudent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack = view.findViewById(R.id.img_class_detail_back);

        //student
        tvStudentName = view.findViewById(R.id.tv_student_detail_name);
        tvStudentDateOfBirth = view.findViewById(R.id.tv_student_detail_dob);
        tvStudentGender = view.findViewById(R.id.tv_student_detail_gender);
        tvRegularScore1 = view.findViewById(R.id.tv_student_detail_regular_1);
        tvRegularScore2 = view.findViewById(R.id.tv_student_detail_regular_2);
        tvRegularScore3 = view.findViewById(R.id.tv_student_detail_regular_3);
        tvMidtermScore = view.findViewById(R.id.tv_student_detail_midterm);
        tvFinalScore = view.findViewById(R.id.tv_student_detail_final);
        tvTotalScore = view.findViewById(R.id.tv_student_detail_total);
        swTerm = view.findViewById(R.id.sw_student_detail_term);
        btnDeleteStudent = view.findViewById(R.id.btn_class_detail_delete);

        // parent
        btnSendFeedback = view.findViewById(R.id.btn_send_feedback);
        edSendFeedBack = view.findViewById(R.id.ed_feedback);
        tvParentName = view.findViewById(R.id.tv_parent_detail_name);
        tvParentDateOfBirth = view.findViewById(R.id.tv_parent_detail_dob);
        tvEmail = view.findViewById(R.id.tv_parent_detail_email);
        tvPhoneNumber = view.findViewById(R.id.tv_parent_detail_phone_number);

        // set ban phim khong che Edittext
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnDeleteStudent.setOnClickListener(v -> {
            // code xoá học sinh ở đây
        });

        swTerm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // set điểm kỳ 2 ở đây
            } else {
                // set điểm kỳ 1 ở đây
            }
        });

        btnSendFeedback.setOnClickListener(v -> {
            // code gửi feedback ở đây
        });

    }
}