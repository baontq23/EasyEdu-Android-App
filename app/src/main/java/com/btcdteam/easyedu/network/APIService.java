package com.btcdteam.easyedu.network;

import com.btcdteam.easyedu.models.Teacher;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @POST("teacher")
    Call<JsonObject> teacherRegister(@Body Teacher teacher);

    @POST("auth/login")
    Call<JsonObject> teacherLogin(@Body JsonObject teacher);

    @POST("parent/login")
    Call<JsonObject> parentLogin(@Body JsonObject parent);

    @GET("teacher/login/{email}")
    Call<JsonObject> teacherLoginWithEmail(@Path("email") String email);

    @GET("parent/login/{email}")
    Call<JsonObject> parentLoginWithEmail(@Path("email") String email);

}
