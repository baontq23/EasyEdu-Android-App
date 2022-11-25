package com.btcdteam.easyedu.fragments.teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.Teacher;
import com.btcdteam.easyedu.network.APIService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfoFragment extends Fragment {
    private EditText edName, edPhoneNumber, edEmail;
    private Button btnSave;
    private ImageView btnBack;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edName = view.findViewById(R.id.ed_acc_info_name);
        edPhoneNumber = view.findViewById(R.id.ed_acc_info_phone_number);
        btnSave = view.findViewById(R.id.btn_acc_info_save);
        btnBack = view.findViewById(R.id.img_acc_info_back);
        edEmail = view.findViewById(R.id.ed_acc_info_email);

        id = getArguments().getInt("teacherId");
        getInfoTeacher(id);
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnSave.setEnabled(true);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTeacher(id);
            }
        });
    }

    private void updateTeacher(int id) {
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("name", edName.getText().toString());
        object.addProperty("phone", edPhoneNumber.getText().toString());
        object.addProperty("email", edEmail.getText().toString());

        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).editTeacher(object);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 404) {
                    Toast.makeText(requireContext(), "Giáo viên không tồn tại", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 409) {
                    Toast.makeText(requireContext(), "Email hoặc số điện thoại đã tồn tại trên hệ thống!", Toast.LENGTH_SHORT).show();
                }
                if (response.code() == 204) {
                    Toast.makeText(requireContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_accountInfoFragment_to_viewClassFragment);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInfoTeacher(int id) {
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getTeacherById(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 404) {
                    Toast.makeText(requireContext(), "Giáo viên không tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    Type type = new TypeToken<Teacher>() {
                    }.getType();
                    Teacher teacher = new Gson().fromJson(response.body().getAsJsonObject("data").toString(), type);
                    edName.setText(teacher.getName());
                    edPhoneNumber.setText(teacher.getPhone());
                    edEmail.setText(teacher.getEmail());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi kết nối Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}