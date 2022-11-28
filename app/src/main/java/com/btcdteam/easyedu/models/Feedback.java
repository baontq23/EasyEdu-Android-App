package com.btcdteam.easyedu.models;

import com.google.gson.annotations.SerializedName;

public class Feedback {
    @SerializedName("feedback_id")
    private int id;
    @SerializedName("feedback_content")
    private String content;
    @SerializedName("feedback_date")
    private long date;
    @SerializedName("teacher_id")
    private int teacherId;
    @SerializedName("student_id")
    private String studentId;

    public Feedback(String content, int teacherId, String studentId) {
        this.content = content;
        this.teacherId = teacherId;
        this.studentId = studentId;
        date = System.currentTimeMillis();
    }

    public Feedback() {
        date = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
