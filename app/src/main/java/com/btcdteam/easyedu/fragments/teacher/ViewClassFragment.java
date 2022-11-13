package com.btcdteam.easyedu.fragments.teacher;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.adapter.ClassroomAdapter;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.Classroom;
import com.btcdteam.easyedu.network.APIService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    ImageView btnInfo;
    FloatingActionButton fabAddClass;
    RecyclerView rcv;
    List<Classroom> list;

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
                if(response.code() == 200) {
                    Type type = new TypeToken<List<Classroom>>(){}.getType();
                    list = new Gson().fromJson(response.body().getAsJsonArray("data").toString(),type);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    ClassroomAdapter adapter = new ClassroomAdapter(list, new ClassroomAdapter.IonClick() {
                        @Override
                        public void onClick() {
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
                Toast.makeText(getContext(), "Không thể kết nối với máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}