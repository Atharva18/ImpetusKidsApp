package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;

/**
 * Created by Chandan Singh on 21-Jan-18.
 */

public class LeaveRequest implements Serializable {
    private String fromDate;
    private String toDate;
    private String reason;
    private String username;
    private long timestamp;

    public LeaveRequest(String fromDate, String toDate, String reason, String username, long timestamp) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.username = username;
        this.timestamp = timestamp;
    }

    public LeaveRequest() {

    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
