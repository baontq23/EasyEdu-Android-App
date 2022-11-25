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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.teacher.FeedbackAdapter;

public class FeedbackFragment extends Fragment {
    RecyclerView rcv;
    ImageView btnBack, btnSendFeedback;
    FeedbackAdapter adapter;
    EditText edFeedback;
    TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv_feedback);
        btnBack = view.findViewById(R.id.img_feedback_back);
        btnSendFeedback = view.findViewById(R.id.btn_send_feedback);
        edFeedback = view.findViewById(R.id.ed_feedback);
        tvTitle = view.findViewById(R.id.tv_feedback_title);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnSendFeedback.setOnClickListener(v -> {
            //xử lý send feedback
        });

        adapter = new FeedbackAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

    }
}