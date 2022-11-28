package com.btcdteam.easyedu.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.teacher.ClassroomAdapter;
import com.btcdteam.easyedu.models.Classroom;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailFragment extends Fragment {
    private ImageView btnBack;
    private TextView tvStudentName,
            tvStudentDateOfBirth,
            tvStudentGender,
            tvRegularScore1,
            tvRegularScore2,
            tvRegularScore3,
            tvMidtermScore,
            tvFinalScore,
            tvTotalScore,
            Tvtotal,
            tvAvg,
            tvClassName,
            tvSubject,
            tvTeacherName;
    private Switch swTerm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack = view.findViewById(R.id.img_parent_student_detail_back);
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
        tvAvg = view.findViewById(R.id.tv_student_detail_avg);
        swTerm = view.findViewById(R.id.sw_student_detail_term);
        Tvtotal = view.findViewById(R.id.Tvtotal);

        tvClassName = view.findViewById(R.id.tv_student_detail_class);
        tvSubject = view.findViewById(R.id.tv_student_detail_subject);
        tvTeacherName = view.findViewById(R.id.tv_student_detail_teacher_name);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        swTerm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                //set điểm kì 2
            }else{
                //set điểm kì 1
            }
        });
    }
}