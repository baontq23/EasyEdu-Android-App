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
import android.widget.Toast;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.ParentAdapter;
import com.btcdteam.easyedu.interfaces.IonClick;
import com.btcdteam.easyedu.models.Parent;

import java.util.ArrayList;
import java.util.List;

public class ParentFragment extends Fragment {
    RecyclerView rcv;
    ParentAdapter adapter;
    List<Parent> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // data test
        list.add(new Parent("abc", "0123"));
        list.add(new Parent("xyz", "0123"));
        list.add(new Parent("hgfhgf", "0123"));
        list.add(new Parent("uoiu", "0123"));
        list.add(new Parent("eww", "0123"));
        list.add(new Parent("arew", "0123"));

        IonClick ionClickBtnOption = new IonClick() {
            @Override
            public void onClick(Object object) {
                Parent parent = (Parent) object;
                Toast.makeText(getContext(), "Clicked: "+ parent.getName(), Toast.LENGTH_SHORT).show();
            }
        };

        IonClick ionClickItem = new IonClick() {
            @Override
            public void onClick(Object object) {

            }
        };

        IonClick ionClickHeader = new IonClick() {
            @Override
            public void onClick(Object object) {

            }
        };
        rcv = view.findViewById(R.id.rcv_parent);
        adapter = new ParentAdapter(list, ionClickItem, ionClickBtnOption, ionClickHeader);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

    }
}