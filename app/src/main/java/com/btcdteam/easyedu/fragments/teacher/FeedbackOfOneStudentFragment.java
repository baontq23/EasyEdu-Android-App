package com.btcdteam.easyedu.fragments.teacher;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.parent.NotificationAdapter;
import com.btcdteam.easyedu.adapter.teacher.FeedbackAdapter;
import com.btcdteam.easyedu.apis.GoogleAPI;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.Feedback;
import com.btcdteam.easyedu.models.Parent;
import com.btcdteam.easyedu.models.StudentDetail;
import com.btcdteam.easyedu.network.APIService;
import com.btcdteam.easyedu.utils.FCMBodyRequest;
import com.btcdteam.easyedu.utils.SnackbarUntil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialogx.dialogs.PopTip;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackOfOneStudentFragment extends Fragment {
    RecyclerView rcv;
    ImageView btnBack, btnSendFeedback;
    NotificationAdapter adapterNoti;
    EditText edFeedback;
    TextView tvTitle;
    List<Parent> list;
    List<StudentDetail> listStudent;
    List<Feedback> listFeedback;
    private int teacherId;
    ArrayList<String> fcmTokens;
    private ProgressBar pbTextFieldLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        teacherId = requireActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE).getInt("session_id", 0);
        return inflater.inflate(R.layout.fragment_feedback_of_one_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv_feedback_of_one_student);
        fcmTokens = new ArrayList<>();
        btnBack = view.findViewById(R.id.img_feedback_back_of_one_student);
        btnSendFeedback = view.findViewById(R.id.btn_send_feedback);
        edFeedback = view.findViewById(R.id.ed_feedback);
        tvTitle = view.findViewById(R.id.tv_feedback_title_of_one_student);
        pbTextFieldLoader = view.findViewById(R.id.pb_text_field_loader);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        getListFeedback();
        btnSendFeedback.setOnClickListener(v -> {
            if (teacherId == 0) {
                Toast.makeText(requireContext(), "Không nhận diện được giáo viên, vui lòng đăng nhập lại!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (edFeedback.getText().toString().trim().isEmpty()) {
                return;
            }
            pbTextFieldLoader.setVisibility(View.VISIBLE);
            btnSendFeedback.setVisibility(View.INVISIBLE);
        });
    }

    private void getListFeedback() {
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getFeedbackByStudent(getArguments().getString("student_id"));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(">>>>>>>", "onResponse: "+ response.code());
                if (response.code() == 200) {
                    Type type = new TypeToken<List<Feedback>>() {
                    }.getType();
                    listFeedback = new Gson().fromJson(response.body().getAsJsonArray("data"), type);
                    adapterNoti = new NotificationAdapter(listFeedback);
                    Log.i(">>>>>>>", "onResponse: "+listFeedback.size());
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    rcv.setLayoutManager(manager);
                    rcv.setAdapter(adapterNoti);
                } else {
                    SnackbarUntil.showWarning(requireView(), "Không có thông báo nào!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                SnackbarUntil.showError(requireView(), "Không thể kết nối tới máy chủ!");
            }
        });
    }

}