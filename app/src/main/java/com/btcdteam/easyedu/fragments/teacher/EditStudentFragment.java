package com.btcdteam.easyedu.fragments.teacher;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.Parent;
import com.btcdteam.easyedu.models.Student;
import com.btcdteam.easyedu.network.APIService;
import com.btcdteam.easyedu.utils.ProgressBarDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStudentFragment extends Fragment implements TextWatcher {
    EditText edStudentName, edStudentDOB,
            edRegularScore1Term1, edRegularScore2Term1, edRegularScore3Term1, edMidtermScoreTerm1, edFinalScoreTerm1,
            edRegularScore1Term2, edRegularScore2Term2, edRegularScore3Term2, edMidtermScoreTerm2, edFinalScoreTerm2,
            edParentPhoneNumber, edParentName, edParentDOB, edParentEmail;
    Spinner spStudentGender;
    String[] items = new String[]{"Nam", "Nữ"};
    ImageView btnBack;
    Button btnSave, btnSearch;
    private String parent_id;
    private int checkParent = 0;
    private ProgressBarDialog progressBarDialog;
    private Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressBarDialog = new ProgressBarDialog(requireActivity());
        calendar = Calendar.getInstance();
        return inflater.inflate(R.layout.fragment_edit_student, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //điều hướng
        btnBack = view.findViewById(R.id.img_edit_student_back);
        btnSave = view.findViewById(R.id.btn_edit_student_save);
        btnSearch = view.findViewById(R.id.btn_parent_search_phone_number);
        // tên, ngày sinh
        edStudentName = view.findViewById(R.id.ed_student_name);
        edStudentDOB = view.findViewById(R.id.ed_student_dob);
        //điểm kỳ 1
        edRegularScore1Term1 = view.findViewById(R.id.ed_regular_1_term_1);
        edRegularScore2Term1 = view.findViewById(R.id.ed_regular_2_term_1);
        edRegularScore3Term1 = view.findViewById(R.id.ed_regular_3_term_1);
        edMidtermScoreTerm1 = view.findViewById(R.id.ed_midterm_term_1);
        edFinalScoreTerm1 = view.findViewById(R.id.ed_final_term_1);
        //điểm kỳ 2
        edRegularScore1Term2 = view.findViewById(R.id.ed_regular_1_term_2);
        edRegularScore2Term2 = view.findViewById(R.id.ed_regular_2_term_2);
        edRegularScore3Term2 = view.findViewById(R.id.ed_regular_3_term_2);
        edMidtermScoreTerm2 = view.findViewById(R.id.ed_midterm_term_2);
        edFinalScoreTerm2 = view.findViewById(R.id.ed_final_term_2);
        //giới tính
        spStudentGender = view.findViewById(R.id.sp_student_gender);
        //Phụ huynh
        edParentPhoneNumber = view.findViewById(R.id.ed_parent_phone_number);
        edParentName = view.findViewById(R.id.ed_parent_name);
        edParentDOB = view.findViewById(R.id.ed_parent_dob);
        edParentEmail = view.findViewById(R.id.ed_parent_email);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spStudentGender.setAdapter(adapter);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        // set ban phim khong che Edittext
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //check null
        edStudentName.addTextChangedListener(this);
        edStudentDOB.addTextChangedListener(this);

        btnSave.setOnClickListener(v -> {
            if (edParentPhoneNumber.getText().toString().trim().length() != 10) {
                Toast.makeText(requireContext(), "Số điện thoại không không hợp lệ hoặc chưa thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBarDialog.setMessage("Loading").show();
            if (checkParent == 1) {
                createStudentWithParent(parent_id);
            } else {
                createStudentWithoutParent();
            }
        });

        btnSearch.setOnClickListener(v -> {
            getParent();
        });

        edStudentDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        edStudentDOB.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (edStudentDOB.getText().toString().trim().length() > 0 && edStudentName.getText().toString().trim().length() > 0) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void getParent() {
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getParentByPhone(edParentPhoneNumber.getText().toString().trim());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBarDialog.dismiss();
                if (response.code() == 200) {
                    checkParent = 1;
                    edParentName.setEnabled(false);
                    edParentDOB.setEnabled(false);
                    edParentEmail.setEnabled(false);
                    parent_id = !response.body().getAsJsonObject("data").get("parent_id").isJsonNull() ? response.body().getAsJsonObject("data").get("parent_id").getAsString() : null;
                    edParentName.setText(!response.body().getAsJsonObject("data").get("parent_name").isJsonNull() ? response.body().getAsJsonObject("data").get("parent_name").getAsString() : null);
                    edParentDOB.setText(!response.body().getAsJsonObject("data").get("parent_dob").isJsonNull() ? response.body().getAsJsonObject("data").get("parent_dob").getAsString() : null);
                    edParentEmail.setText(!response.body().getAsJsonObject("data").get("parent_email").isJsonNull() ? response.body().getAsJsonObject("data").get("parent_email").getAsString() : null);
                } else {
                    checkParent = 0;
                    edParentName.setEnabled(true);
                    edParentDOB.setEnabled(true);
                    edParentEmail.setEnabled(true);
                    Toast.makeText(requireContext(), "Không có phụ huynh nào, vui lòng tạo mới!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                progressBarDialog.dismiss();
                Toast.makeText(requireContext(), "Không thể kết nối tới máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createStudentWithParent(String parentId) {
        Student student = new Student();
        student.setName(edStudentName.getText().toString().trim());
        student.setDob(edStudentDOB.getText().toString().trim());
        student.setGender(spStudentGender.getSelectedItem().toString());
        student.setParentId(checkParent == 1 ? parent_id : parentId);
        JsonObject body = new JsonObject();
        body.addProperty("id", student.getId());
        body.addProperty("name", student.getName());
        body.addProperty("gender", student.getGender());
        body.addProperty("dob", student.getDob());
        body.addProperty("parent_id", student.getParentId());
        body.addProperty("classroom_id", getArguments().getInt("classroom_id"));
        JsonArray jsonElements = new JsonArray();
        JsonObject semester1 = new JsonObject();
        semester1.addProperty("regular_score_1", edRegularScore1Term1.getText().toString().trim().equals("") ? null : edRegularScore1Term1.getText().toString().trim());
        semester1.addProperty("regular_score_2", edRegularScore2Term1.getText().toString().trim().equals("") ? null : edRegularScore2Term1.getText().toString().trim());
        semester1.addProperty("regular_score_3", edRegularScore3Term1.getText().toString().trim().equals("") ? null : edRegularScore3Term1.getText().toString().trim());
        semester1.addProperty("midterm_score", edMidtermScoreTerm1.getText().toString().trim().equals("") ? null : edMidtermScoreTerm1.getText().toString().trim());
        semester1.addProperty("final_score", edFinalScoreTerm1.getText().toString().trim().equals("") ? null : edFinalScoreTerm1.getText().toString().trim());
        semester1.addProperty("semester", 1);
        JsonObject semester2 = new JsonObject();
        semester2.addProperty("regular_score_1", edRegularScore1Term2.getText().toString().trim().equals("") ? null : edRegularScore1Term2.getText().toString().trim());
        semester2.addProperty("regular_score_2", edRegularScore2Term2.getText().toString().trim().equals("") ? null : edRegularScore2Term2.getText().toString().trim());
        semester2.addProperty("regular_score_3", edRegularScore3Term2.getText().toString().trim().equals("") ? null : edRegularScore3Term2.getText().toString().trim());
        semester2.addProperty("midterm_score", edMidtermScoreTerm2.getText().toString().trim().equals("") ? null : edMidtermScoreTerm2.getText().toString().trim());
        semester2.addProperty("final_score", edFinalScoreTerm2.getText().toString().trim().equals("") ? null : edFinalScoreTerm2.getText().toString().trim());
        semester2.addProperty("semester", 2);
        jsonElements.add(semester1);
        jsonElements.add(semester2);
        body.add("scores", jsonElements);
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).createStudentHandMade(body);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressBarDialog.dismiss();
                if (response.code() == 201) {
                    Toast.makeText(requireContext(), "Tạo học sinh thành công!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(requireContext(), "Thông tin không hợp lệ!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Tạo lớp học thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressBarDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(requireContext(), "Không thể kết nối tới máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createStudentWithoutParent() {
        Parent parent = new Parent(edParentName.getText().toString().trim(), edParentPhoneNumber.getText().toString().trim());
        if (parent.getName().equals("") || parent.getPhone().equals("")) {
            Toast.makeText(requireContext(), "Chưa chọn phụ huynh!", Toast.LENGTH_SHORT).show();
        } else {
            Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).createParent(parent);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressBarDialog.dismiss();
                    if (response.code() == 201) {
                        createStudentWithParent(parent.getId());
                    } else if (response.code() == 409) {
                        Toast.makeText(requireContext(), "Số điện thoại đã tồn tại!", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400) {
                        Toast.makeText(requireContext(), "Thông tin không hợp lệ!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Tạo phụ huynh thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressBarDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(requireContext(), "Không thể kết nối tới máy chủ!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}