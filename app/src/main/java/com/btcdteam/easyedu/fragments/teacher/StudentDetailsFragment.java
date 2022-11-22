package com.btcdteam.easyedu.fragments.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.StudentDetail;
import com.btcdteam.easyedu.network.APIService;
import com.btcdteam.easyedu.utils.ProgressBarDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailsFragment extends Fragment {
    private ImageView btnBack, btnSendFeedback;
    private ProgressBarDialog progressBarDialog;

    private TextView tvStudentName,
            tvStudentDateOfBirth,
            tvStudentGender,
            tvRegularScore1,
            tvRegularScore2,
            tvRegularScore3,
            tvMidtermScore,
            tvFinalScore,
            tvTotalScore,
            tvParentName,
            tvParentDateOfBirth,
            tvEmail,
            Tvtotal,
            tvAvg,
            tvPhoneNumber;

    private EditText edSendFeedBack;
    private Switch swTerm;
    private int postion;
    Button btnDeleteStudent;
    List<StudentDetail> studentDetails, studentDetails1, studentDetails2;
    String studentId, studentName;
    int classId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressBarDialog = new ProgressBarDialog(requireActivity());
        return inflater.inflate(R.layout.fragment_student_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack = view.findViewById(R.id.img_class_detail_back);
        studentDetails = new ArrayList<>();
        studentDetails1 = new ArrayList<>();
        studentDetails2 = new ArrayList<>();
        studentId = getArguments().getString("studentId");
        classId = getArguments().getInt("classRoomId");
        studentName = getArguments().getString("studentName");

        //student
        tvStudentName = view.findViewById(R.id.tv_student_detail_name);
        tvStudentDateOfBirth = view.findViewById(R.id.tv_student_detail_dob);
        tvStudentGender = view.findViewById(R.id.tv_student_detail_gender);
        tvRegularScore1 = view.findViewById(R.id.tv_student_detail_regular_1);
        tvRegularScore2 = view.findViewById(R.id.tv_student_detail_regular_2);
        tvRegularScore3 = view.findViewById(R.id.tv_student_detail_regular_3);
        tvMidtermScore = view.findViewById(R.id.tv_student_detail_midterm);
        tvFinalScore = view.findViewById(R.id.tv_student_detail_final);
        tvTotalScore = view.findViewById(R.id.tv_student_detail_total);
        tvAvg = view.findViewById(R.id.tv_student_detail_avg);
        swTerm = view.findViewById(R.id.sw_student_detail_term);
        btnDeleteStudent = view.findViewById(R.id.btn_class_detail_delete);
        Tvtotal = view.findViewById(R.id.Tvtotal);

        // parent
        btnSendFeedback = view.findViewById(R.id.btn_send_feedback);
        edSendFeedBack = view.findViewById(R.id.ed_feedback);
        tvParentName = view.findViewById(R.id.tv_parent_detail_name);
        tvParentDateOfBirth = view.findViewById(R.id.tv_parent_detail_dob);
        tvEmail = view.findViewById(R.id.tv_parent_detail_email);
        tvPhoneNumber = view.findViewById(R.id.tv_parent_detail_phone_number);
        progressBarDialog.setMessage("Loading").show();

        getInfoTeacherAndParent();
        // set ban phim khong che Edittext
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        btnDeleteStudent.setOnClickListener(v -> {

            MessageDialog messageDialog = new MessageDialog("Xóa học sinh", "Bạn có muốn xóa học sinh: " + studentName + " Không ?", "Có", "Không")
                    .setButtonOrientation(LinearLayout.HORIZONTAL)
                    .setOkButton(new OnDialogButtonClickListener<MessageDialog>() {
                        @Override
                        public boolean onClick(MessageDialog dialog, View v) {
                            deleteStudent(studentId,classId);
                            return false;
                        }
                    });
            messageDialog.show();
        });


        swTerm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setValues(studentDetails2);
            } else {
                setValues(studentDetails1);
            }
        });

        btnSendFeedback.setOnClickListener(v -> {
            // code gửi feedback ở đây
        });

    }

    void deleteStudent(String studentId, int classId) {
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).deleteStudentById(studentId, classId);
        Log.e(">>>>>>>>>>>>>>>", "deleteStudent: "+studentId+" / "+classId );
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 204) {
                    Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_studentDetailsFragment_to_classInfoFragment2);
                } else {
                    Toast.makeText(requireContext(), "Không tìm thấy thông tin học sinh", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getInfoTeacherAndParent() {
        String studentId = getArguments().getString("studentId");
        int classId = getArguments().getInt("classRoomId");
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getInfoParentAndStudent(studentId, classId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBarDialog.dismiss();
                if (response.code() == 200) {
                    Type type = new TypeToken<List<StudentDetail>>() {
                    }.getType();
                    studentDetails = new Gson().fromJson(response.body().getAsJsonArray("data"), type);
                    for (StudentDetail o : studentDetails) {
                        if (o.getSemester() % 2 == 0) {
                            studentDetails2.add(o);
                        } else {
                            studentDetails1.add(o);
                            setValues(studentDetails1);
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Học sinh không tồn tại !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setValues(List<StudentDetail> list) {
        for (StudentDetail o : list) {
            tvTotalScore.setVisibility(View.GONE);
            Tvtotal.setVisibility(View.GONE);
            float total = ((o.getRegularScore1() + o.getRegularScore2() + o.getRegularScore3() + (2 * (o.getMidtermScore())) + (3 * (o.getFinalScore())))) / (o.getRegularScore1()) + 5;
            tvStudentName.setText(o.getName());
            tvStudentDateOfBirth.setText(o.getDob());
            tvStudentGender.setText(o.getStudentGender());
            tvRegularScore1.setText(String.valueOf(o.getRegularScore1()));
            tvRegularScore2.setText(String.valueOf(o.getRegularScore2()));
            tvRegularScore3.setText(String.valueOf(o.getRegularScore2()));
            tvMidtermScore.setText(String.valueOf(o.getMidtermScore()));
            tvFinalScore.setText(String.valueOf(o.getFinalScore()));
            tvParentName.setText(o.getParentName());
            tvParentDateOfBirth.setText(o.getParentDob());
            tvEmail.setText(o.getParentEmail());
            if (Float.isNaN(total)) {
                tvAvg.setText("0.0");
            } else {
                tvAvg.setText(String.valueOf(total));
            }
            tvPhoneNumber.setText(o.getParentPhone());
        }
    }
}