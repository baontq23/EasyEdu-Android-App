package com.btcdteam.easyedu.fragments.teacher.subfragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.teacher.ParentAdapter;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.Parent;
import com.btcdteam.easyedu.network.APIService;
import com.btcdteam.easyedu.utils.SnackbarUntil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack;
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentFragment extends Fragment implements ParentAdapter.ParentItemListener {
    private RecyclerView rcv;
    private ParentAdapter adapter;
    private List<Parent> list;

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
        rcv = view.findViewById(R.id.rcv_parent);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(manager);
    }

    private void getListParent() {
        SharedPreferences preferences = requireContext().getSharedPreferences("CLASSROOM_ID", Context.MODE_PRIVATE);
        int classroomId = preferences.getInt("classroomId", 0);

        retrofit2.Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getListParentByIdClassRoom(classroomId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Type type = new TypeToken<List<Parent>>() {
                    }.getType();
                    list = new ArrayList<>();
                    list = new Gson().fromJson(response.body().getAsJsonArray("data"), type);
                    adapter = new ParentAdapter(list, ParentFragment.this);
                    rcv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getListParent();
    }

    @Override
    public void onItemClick(int position, Parent parent) {
        BottomMenu.show(new String[]{"Gọi", "Gửi tin nhắn SMS", "Đặt lại mật khẩu"})
                .setTitle(parent.getName())
                .setMessage("Số điện thoại: " + parent.getPhone())
                .setOnIconChangeCallBack(new OnIconChangeCallBack(true) {
                    @Override
                    public int getIcon(BaseDialog bottomMenu, int index, String menuText) {
                        switch (index) {
                            case 0:
                                return R.drawable.ic_outline_call_24;
                            case 1:
                                return R.drawable.ic_outline_sms_24;
                            case 2:
                                return R.drawable.ic_baseline_key_24;
                        }
                        return 0;
                    }
                })
                .setOnMenuItemClickListener(new OnMenuItemClickListener<BottomMenu>() {
                    @Override
                    public boolean onClick(BottomMenu dialog, CharSequence text, int index) {
                        if (index == 0) {
                            call(parent.getPhone());
                        } else if (index == 1) {
                            sendSMS(parent.getPhone());
                        } else if (index == 2) {
                            MessageDialog.show("Thông báo", "Xác nhận đặt lại mật khẩu cho phụ huynh " + parent.getName() + " ?", "Xác nhận", "Huỷ bỏ")
                                    .setOkButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
                                        @Override
                                        public boolean onClick(MessageDialog dialog, View v) {
                                            handleResetPassword(parent.getId());
                                            return false;
                                        }
                                    }).show();
                        } else {
                            return false;
                        }
                        return false;
                    }
                });
    }

    private void handleResetPassword(String id) {
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).resetParentPassword(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 204) {
                    MessageDialog.show("Thông báo", "Đã đặt lại mật khẩu cho phụ huynh. Mật khẩu mới là 123456", "Đóng").show();
                } else {
                    SnackbarUntil.showWarning(requireView(), "Đã xảy ra lỗi, vui lòng thao tác lại");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                SnackbarUntil.showError(requireView(), "Không thể kết nối đến máy chủ!");
            }
        });
    }

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void sendSMS(String phone) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phone));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "Không có ứng dụng mở tin nhắn!", Toast.LENGTH_SHORT).show();
        }
    }
}