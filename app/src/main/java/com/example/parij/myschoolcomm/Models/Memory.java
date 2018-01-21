package com.example.parij.myschoolcomm.Models;

/**
 * Created by ankush on 21/1/18.
 */

public class Memory {
    private long timestamp;
    private String url;

    public Memory(long timestamp, String url) {
        this.timestamp = timestamp;
        this.url = url;
    }

    public Memory() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
