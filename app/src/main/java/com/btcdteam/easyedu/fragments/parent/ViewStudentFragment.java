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
import com.btcdteam.easyedu.adapter.parent.StudentAdapter;
import com.btcdteam.easyedu.models.Parent;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentFragment extends Fragment implements StudentAdapter.StudentItemListener {
    private ImageView btnInfo;
    private RecyclerView rcv;
    private LinearProgressIndicator lpiClass;
    private StudentAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lpiClass = view.findViewById(R.id.lpi_parent_student);
        btnInfo = view.findViewById(R.id.img_item_parent_student_info);
        rcv = view.findViewById(R.id.rcv_item_parent_student);
        adapter = new StudentAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        // navigation
        Navigation.findNavController(requireActivity(), R.id.nav_host_parent).navigate(R.id.action_viewStudentFragment_to_studentDetailFragment);
    }
}