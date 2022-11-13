package com.btcdteam.easyedu.models;

import com.google.gson.annotations.SerializedName;

public class Classroom {
    private int id;
    private String name;
    private String description;
    private String subject;
    @SerializedName("teacher_id")
    private int teacherId;

    public Classroom() {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
