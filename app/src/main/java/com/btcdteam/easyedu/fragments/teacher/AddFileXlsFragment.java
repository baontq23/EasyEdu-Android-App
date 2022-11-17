package com.btcdteam.easyedu.fragments.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.PreviewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddFileXlsFragment extends Fragment {

    private FloatingActionButton btnAddFile;
    private RecyclerView rcv;
    private Button btnSave;
    private ImageView btnBack;
    private PreviewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_file_xls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddFile = view.findViewById(R.id.fab_add_xls_file);
        btnSave = view.findViewById(R.id.btn_add_file_save);
        rcv = view.findViewById(R.id.rcv_add_file);
        btnBack = view.findViewById(R.id.img_add_file_back);
        adapter = new PreviewAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnSave.setOnClickListener(v -> {
            //code lưu ds đọc từ file
        });

        btnAddFile.setOnClickListener(v -> {
            //code chuyển màn hình chọn file và đọc file
        });
    }
}