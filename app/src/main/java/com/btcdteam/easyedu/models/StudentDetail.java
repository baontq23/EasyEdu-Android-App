package com.btcdteam.easyedu.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentDetail implements Serializable {
    private int id;
    @SerializedName("classroom_id")
    private int classroomId;
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("student_name")
    private String name;
    @SerializedName("student_dob")
    private String dob;
    @SerializedName("regular_score_1")
    private float regularScore1;
    @SerializedName("regular_score_2")
    private float regularScore2;
    @SerializedName("regular_score_3")
    private float regularScore3;
    @SerializedName("midterm_score")
    private float midtermScore;
    @SerializedName("final_score")
    private float finalScore;
    private int semester;
    @SerializedName("parent_name")
    private String parentName;
    @SerializedName("parent_email")
    private String parentEmail;
    @SerializedName("parent_dob")
    private String parentDob;
    @SerializedName("parent_phone")
    private String parentPhone;
    @SerializedName("student_gender")
    private String studentGender;


    public StudentDetail(int id, int classroomId, String studentId, String name, int semester) {
        this.id = id;
        this.classroomId = classroomId;
        this.studentId = studentId;
        this.name = name;
        this.semester = semester;
    }

    public StudentDetail() {

    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public float getRegularScore1() {
        return regularScore1;
    }

    public void setRegularScore1(float regularScore1) {
        this.regularScore1 = regularScore1;
    }

    public float getRegularScore2() {
        return regularScore2;
    }

    public void setRegularScore2(float regularScore2) {
        this.regularScore2 = regularScore2;
    }

    public float getRegularScore3() {
        return regularScore3;
    }

    public void setRegularScore3(float regularScore3) {
        this.regularScore3 = regularScore3;
    }

    public float getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(float midtermScore) {
        this.midtermScore = midtermScore;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentDob() {
        return parentDob;
    }

    public void setParentDob(String parentDob) {
        this.parentDob = parentDob;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }
}
