package com.example.parij.myschoolcomm;

/**
 * Created by Ankush on 12-01-2018.
 */

public class Student {
    private String program;
    private String batch;
    private int rollNo;
    private String name;
    private String username;

    public Student(String program, String batch, int rollNo, String name, String username) {
        this.program = program;
        this.batch = batch;
        this.rollNo = rollNo;
        this.name = name;
        this.username = username;
    }

    public Student() {
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
