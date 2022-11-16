package com.btcdteam.easyedu.fragments.teacher;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.ClassroomAdapter;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.interfaces.IonClick;
import com.btcdteam.easyedu.models.Classroom;
import com.btcdteam.easyedu.network.APIService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewClassFragment extends Fragment {
    private ImageView btnInfo;
    private FloatingActionButton fabAddClass;
    private RecyclerView rcv;
    private List<Classroom> list;
    private LinearProgressIndicator lpiClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_class, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lpiClass = view.findViewById(R.id.lpi_class);
        btnInfo = view.findViewById(R.id.img_item_class_info);
        rcv = view.findViewById(R.id.rcv_item_class);
        fabAddClass = view.findViewById(R.id.fab_add_class);

        fabAddClass.setOnClickListener(v -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_viewClassFragment_to_createClassFragment);
        });

        btnInfo.setOnClickListener(v -> {

        });

        list = new ArrayList<>();
        getList();
    }


    private void getList() {
        SharedPreferences shared = requireActivity().getSharedPreferences("SESSION", MODE_PRIVATE);
        Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).getListClassroom(shared.getInt("session_id", 0));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                lpiClass.hide();
                if (response.code() == 200) {
                    Type type = new TypeToken<List<Classroom>>() {
                    }.getType();
                    list = new Gson().fromJson(response.body().getAsJsonArray("data").toString(), type);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    ClassroomAdapter adapter = new ClassroomAdapter(list, new IonClick() {
                        @Override
                        public void onClick(Object object) {
                            saveClassroomId(((Classroom) object).getId(), ((Classroom) object).getName());
                            Navigation.findNavController(requireActivity(), R.id.nav_host_teacher).navigate(R.id.action_viewClassFragment_to_classInfoFragment);
                        }
                    });
                    rcv.setLayoutManager(manager);
                    rcv.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Không có lớp nào!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                lpiClass.hide();
                Toast.makeText(getContext(), "Không thể kết nối với máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveClassroomId(int classroomId, String classroomName) {
        SharedPreferences.Editor editor = requireContext().getSharedPreferences("CLASSROOM_ID", MODE_PRIVATE).edit();
        editor.clear();
        editor.putInt("classroomId", classroomId);
        editor.putString("classroomName", classroomName);
        editor.apply();
    }
}