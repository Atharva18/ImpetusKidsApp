package com.example.parij.myschoolcomm;

import java.util.Calendar;

/**
 * Created by parij on 25/12/2017.
 */

public class Broadcast {
    String date;
    long timestamp;

    public Broadcast() {
    }



    public Broadcast(String date, long timestamp) {
        this.date = date;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return date;
    }

    public void setMsg(String msg) {
        this.date = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
