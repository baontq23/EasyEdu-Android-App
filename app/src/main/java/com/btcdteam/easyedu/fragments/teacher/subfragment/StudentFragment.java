package com.btcdteam.easyedu.fragments.teacher.subfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.StudentAdapter;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.StudentDetail;
import com.btcdteam.easyedu.network.APIService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFragment extends Fragment implements StudentAdapter.StudentItemListener {
    private RecyclerView rcv;
    private StudentAdapter adapter;
    private List<StudentDetail> studentDetailList = new ArrayList<>();
    private List<StudentDetail> studentDetailLis01 = new ArrayList<>();
    private SearchView searchView;
    private String text = null;
    private TextView tvStatusList;
    private SharedPreferences preferences;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle.getSerializable("Array") != null) {
                studentDetailList = (List<StudentDetail>) bundle.getSerializable("Array");
                adapter = new StudentAdapter(studentDetailList, StudentFragment.this);
                rcv.setAdapter(adapter);
                checkListStatus();
            }
        }
    };

    BroadcastReceiver receiverName = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("search") != null) {
                text = intent.getStringExtra("search").trim();
                searchView.setQuery(text, true);
            } else {
                Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                searchView.setQuery(null, true);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = requireContext().getSharedPreferences("CLASSROOM_ID", Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        requireContext().registerReceiver(receiver, new IntentFilter("ACTION"));
        requireContext().registerReceiver(receiverName, new IntentFilter("SEARCH"));
        getListStudent();
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(receiver);
        requireActivity().unregisterReceiver(receiverName);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv_student);
        searchView = view.findViewById(R.id.reSearchView);
        tvStatusList = view.findViewById(R.id.tv_status_list);
        LinearLayoutManager manager = new GridLayoutManager(getContext(), 1);
        rcv.setLayoutManager(manager);
        getListStudent();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null)
                    adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.getFilter().filter("");
                return false;
            }
        });

    }

    private void getListStudent() {
        int classroomId = preferences.getInt("classroomId", 0);
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getListStudentByIdClassRoom(classroomId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Type type = new TypeToken<List<StudentDetail>>() {
                    }.getType();
                    studentDetailList = new ArrayList<>();
                    studentDetailLis01 = new ArrayList<>();
                    studentDetailList = new Gson().fromJson(response.body().getAsJsonArray("data"), type);
                    for (StudentDetail o : studentDetailList) {
                        if (o.getSemester() % 2 != 0) {
                            studentDetailLis01.add(o);
                        }
                    }
                    adapter = new StudentAdapter(studentDetailLis01, StudentFragment.this);
                    rcv.setAdapter(adapter);
                }
                checkListStatus();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position, StudentDetail student) {
        Bundle bundle = new Bundle();
        bundle.putString("studentId", student.getStudentId());
        bundle.putInt("classRoomId", student.getClassroomId());
        bundle.putInt("position", position);
        bundle.putString("studentName", student.getName());
        Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_classInfoFragment_to_studentDetailsFragment, bundle);
    }

    void deleteStudent(int position, String id, int idClass) {
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).deleteStudentById(id, idClass);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 204) {
                    Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    studentDetailLis01.remove(position);
                    adapter.setList(studentDetailLis01);
                    adapter.notifyItemRemoved(position);
                } else {
                    Toast.makeText(requireContext(), "Không tìm thấy thông tin học sinh", Toast.LENGTH_SHORT).show();
                }
                checkListStatus();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
                checkListStatus();
            }
        });

    }

    private void checkListStatus() {
        if (adapter != null) {
            if (adapter.getItemCount() > 0) {
                tvStatusList.setVisibility(View.GONE);
            } else {
                tvStatusList.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onOptionClick(int position, StudentDetail student) {
        BottomMenu.show(new String[]{"Cập nhật", "Danh sách thông báo", "Xóa"})
                .setMessage("Học sinh: " + student.getName())
                .setOnMenuItemClickListener(new OnMenuItemClickListener<BottomMenu>() {
                    @Override
                    public boolean onClick(BottomMenu dialog, CharSequence text, int index) {
                        switch (index) {
                            case 0:
                                Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_classInfoFragment_to_editStudentFragment);
                                BottomMenu.cleanAll();
                                return false;
                            case 1:
                                Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_classInfoFragment_to_feedbackFragment);
                                BottomMenu.cleanAll();
                                return false;
                            case 2:
                                MessageDialog messageDialog = new MessageDialog("Xóa học sinh", "Bạn có muốn xóa học sinh: " + student.getName() + " Không ?", "Có", "Không")
                                        .setButtonOrientation(LinearLayout.HORIZONTAL)
                                        .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                                            @Override
                                            public boolean onClick(MessageDialog dialog, View v) {
                                                deleteStudent(position, student.getStudentId(), student.getClassroomId());
                                                return false;
                                            }
                                        });
                                messageDialog.show();
                                return false;
                            default:
                                return false;
                        }
                    }
                });
    }
}