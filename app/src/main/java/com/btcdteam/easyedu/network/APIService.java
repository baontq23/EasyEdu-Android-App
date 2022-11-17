package com.btcdteam.easyedu.network;

import com.btcdteam.easyedu.models.Classroom;
import com.btcdteam.easyedu.models.Teacher;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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

    @POST("classroom")
    Call<JsonObject> createClassroom(@Body Classroom classroom);

    @GET("classroom/teacherId/{teacherId}")
    Call<JsonObject> getListClassroom(@Path("teacherId") int teacherId);

    @GET("classuser/{idClass}")
    Call<JsonObject> getListStudentByIdClassRoom(@Path("idClass") int classId);

    @GET("parent/{idClassroom}")
    Call<JsonObject> getListParentByIdClassRoom(@Path("idClassroom") int classroomId);

    @DELETE("student/{id}")
    Call<JsonObject> deleteStudentById(@Path("id") String id);

    @DELETE("classroom/{id}")
    Call<JsonObject> deleteClassRoomById(@Path("id") int id);

    @GET("classuser/getinfo/{id}/{idClass}")
    Call<JsonObject> getInfoParentAndStudent(@Path("id") String id, @Path("idClass") int idClass);

    @PATCH("classroom")
    Call<JsonObject> updateClassRoomById(@Body Classroom classroom);
}
