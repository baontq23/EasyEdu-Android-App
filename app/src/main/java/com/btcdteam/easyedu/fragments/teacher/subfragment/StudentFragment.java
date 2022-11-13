package com.btcdteam.easyedu.fragments.teacher.subfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.StudentAdapter;
import com.btcdteam.easyedu.interfaces.IonClick;
import com.btcdteam.easyedu.models.Student;

import java.util.List;

public class StudentFragment extends Fragment {
    RecyclerView rcv;
    StudentAdapter adapter;
    List<Student> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IonClick headerOnclick = () -> {

        };

        IonClick itemStudentOnclick = () -> {

        };

        rcv = view.findViewById(R.id.rcv_student);
        adapter = new StudentAdapter(list, itemStudentOnclick, headerOnclick);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }
}