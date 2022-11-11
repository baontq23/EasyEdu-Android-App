package com.btcdteam.easyedu.fragments.teacher;

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
import com.btcdteam.easyedu.adapter.TestAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewClassFragment extends Fragment {
    ImageView btnInfo;
    FloatingActionButton fabAddClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_class, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnInfo = view.findViewById(R.id.img_item_class_info);
        RecyclerView rcv = view.findViewById(R.id.rcv_item_class);
        fabAddClass = view.findViewById(R.id.fab_add_class);

        fabAddClass.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_viewClassFragment_to_createClassFragment);
        });

        btnInfo.setOnClickListener(v -> {

        });

        //test layout
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        TestAdapter adapter = new TestAdapter(new TestAdapter.IonClick() {
            @Override
            public void onClick() {
                Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_viewClassFragment_to_classInfoFragment);
            }
        });
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }
}