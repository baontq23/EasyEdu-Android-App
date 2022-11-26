package com.btcdteam.easyedu.fragments.parent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.parent.StudentDetailAdapter;

public class StudentDetailFragment extends Fragment {
    private RecyclerView rcv;
    private ImageView btnBack;
    private StudentDetailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv_parent_student_detail);
        btnBack = view.findViewById(R.id.img_parent_student_detail_back);
        adapter = new StudentDetailAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
}