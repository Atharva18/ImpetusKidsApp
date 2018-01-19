package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;

/**
 * Created by Chandan Singh on 18-Jan-18.
 */

public class Announcement implements Serializable {
    private String title;
    private String detail;
    private int program;
    private long timestamp;

    public Announcement(String title, String detail, int program, long timestamp) {
        this.title = title;
        this.detail = detail;
        this.program = program;
        this.timestamp = timestamp;
    }

    public Announcement() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
