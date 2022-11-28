package com.btcdteam.easyedu.fragments.parent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.activity.AuthActivity;
import com.btcdteam.easyedu.adapter.parent.StudentAdapter;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentFragment extends Fragment implements StudentAdapter.StudentItemListener {
    private ImageView btnInfo, btnNoti;
    private RecyclerView rcv;
    private LinearProgressIndicator lpiClass;
    private StudentAdapter adapter;
    private List<String> list = new ArrayList<>();
    private SharedPreferences shared;
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
        btnNoti = view.findViewById(R.id.img_item_parent_student_noti);
        rcv = view.findViewById(R.id.rcv_item_parent_student);
        adapter = new StudentAdapter(this);

        btnNoti.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_parent).navigate(R.id.action_viewStudentFragment_to_notificationFragment);
        });

        btnInfo.setOnClickListener(this::showPopupMenu);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.menu_teacher_option);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle;
                switch (item.getItemId()) {
                    case R.id.menu_account_info:
                        shared = requireActivity().getSharedPreferences("SESSION", MODE_PRIVATE);
                        Toast.makeText(requireContext(), shared.getString("session_name", "None"), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_change_password:
                        shared = requireActivity().getSharedPreferences("SESSION", MODE_PRIVATE);

                        return true;
                    case R.id.menu_logout:
                        MessageDialog.show("Đăng xuất", "Bạn có muốn đăng xuất không ?", "Có", "Không").setOkButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
                            @Override
                            public boolean onClick(MessageDialog dialog, View v) {
                                SharedPreferences.Editor editor = requireActivity().getSharedPreferences("SESSION", MODE_PRIVATE).edit();
                                editor.clear().apply();
                                startActivity(new Intent(requireActivity(), AuthActivity.class));
                                requireActivity().finish();
                                return false;
                            }
                        }).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    @Override
    public void onItemClick(int position) {
        // navigation
        Navigation.findNavController(requireActivity(), R.id.nav_host_parent).navigate(R.id.action_viewStudentFragment_to_studentDetailFragment);
    }
}