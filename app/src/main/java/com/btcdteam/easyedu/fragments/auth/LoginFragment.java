package com.btcdteam.easyedu.fragments.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.btcdteam.easyedu.R;
import com.btcdteam.easyedu.activity.ParentActivity;
import com.btcdteam.easyedu.activity.TeacherActivity;
import com.btcdteam.easyedu.apis.ServerAPI;
import com.btcdteam.easyedu.models.Parent;
import com.btcdteam.easyedu.models.Teacher;
import com.btcdteam.easyedu.network.APIService;
import com.btcdteam.easyedu.utils.ProgressBarDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.dialogs.PopTip;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    TextInputLayout inputLayoutLoginPhoneNumber, inputLayoutLoginPassword;
    TextInputEditText edLoginPhoneNumber, edLoginPassword;
    Button btnLogin, btnLoginGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    String role;
    private ProgressBarDialog progressBarDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        // Inflate the layout for this fragment
        progressBarDialog = new ProgressBarDialog(requireActivity());
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        role = getArguments().getString("role");
        inputLayoutLoginPhoneNumber = view.findViewById(R.id.ed_layout_login_phone_number);
        inputLayoutLoginPassword = view.findViewById(R.id.ed_layout_login_password);
        edLoginPhoneNumber = view.findViewById(R.id.ed_login_phone_number);
        edLoginPassword = view.findViewById(R.id.ed_login_password);
        btnLogin = view.findViewById(R.id.btn_login_login);
        btnLoginGoogle = view.findViewById(R.id.btn_login_login_google);


        btnLogin.setOnClickListener(v -> {
            String phone = edLoginPhoneNumber.getText().toString();
            String password = edLoginPassword.getText().toString();

            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireActivity(), "Không được để trống các trường", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBarDialog.setMessage("Loading").show();
            JsonObject object = new JsonObject();
            object.addProperty("phone", phone);
            object.addProperty("password", password);

            if (role.equals("teacher")) {
                Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).teacherLogin(object);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressBarDialog.dismiss();
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Type userListType = new TypeToken<Teacher>() {
                            }.getType();
                            Teacher teacher = new Gson().fromJson(response.body().getAsJsonObject("data"), userListType);
                            sharedPreferencesTeacher(role, teacher.getId(), teacher.getName(), teacher.getEmail(), teacher.getPhone(), teacher.getDob());
                            requireActivity().startActivity(new Intent(requireActivity(), TeacherActivity.class));
                            requireActivity().finish();
                        } else if (response.code() == 401) {
                            Toast.makeText(getContext(), "Sai thông tin tài khoản", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressBarDialog.dismiss();
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "Không thế kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Call<JsonObject> call = ServerAPI.getInstance().create(APIService.class).parentLogin(object);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressBarDialog.dismiss();
                        if (response.code() == 200) {
                            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Type userListType = new TypeToken<Parent>() {
                            }.getType();
                            Parent parent = new Gson().fromJson(response.body().getAsJsonObject("data"), userListType);
                            sharedPreferencesParent(role, parent.getId(), parent.getName(), parent.getEmail(), parent.getPhone(), parent.getDob(), parent.getFcmToken());
                            requireActivity().startActivity(new Intent(requireActivity(), ParentActivity.class));
                            requireActivity().finish();
                        } else if (response.code() == 404) {
                            Toast.makeText(getContext(), "Sai thông tin tài khoản", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {
                            Toast.makeText(getContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Đăng nhập thấy bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressBarDialog.dismiss();
                        t.printStackTrace();
                        Toast.makeText(requireContext(), "Không thế kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });

        btnLoginGoogle.setOnClickListener(v -> {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (acct != null) {
                new MessageDialog("Thông tin", "Email: " + acct.getEmail(), "Logout", "Close").setOkButtonClickListener(new OnDialogButtonClickListener<MessageDialog>() {
                    @Override
                    public boolean onClick(MessageDialog dialog, View v) {
                        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(requireContext(), "Logout successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return false;
                    }
                }).show();
            } else {
                signInWithGoogle();
            }
        });
    }

    private final ActivityResultLauncher<Intent> loginLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getData() == null) {
                //no data present
                return;
            }
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            handleSignInResult(task);
        }
    });

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        loginLauncher.launch(signInIntent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(requireContext(), "Email: " + account.getEmail(), Toast.LENGTH_SHORT).show();
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void sharedPreferencesTeacher(String role, int id, String name, String email, String phone, String dob) {
        SharedPreferences.Editor edt = requireActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE).edit();
        edt.clear();
        edt.putString("session_role", role);
        edt.putInt("session_id", id);
        edt.putString("session_name", name);
        edt.putString("session_email", email);
        edt.putString("session_phone", phone);
        edt.putString("session_dob", dob);
        edt.apply();
    }

    private void sharedPreferencesParent(String role, String id, String name, String email, String phone, String dob, String fcm_token) {
        SharedPreferences.Editor edt = requireActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE).edit();
        edt.clear();
        edt.putString("session_role", role);
        edt.putString("session_id", id);
        edt.putString("session_name", name);
        edt.putString("session_email", email);
        edt.putString("session_phone", phone);
        edt.putString("session_dob", dob);
        edt.putString("session_fcmToken", fcm_token);
        edt.apply();
    }

}