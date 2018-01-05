package com.example.parij.myschoolcomm;

/**
 * Created by parij on 25/12/2017.
 */

public class Message {
    String reason;
    String fromdate;
    String todate;
    long timestamp;
    String sender;

    public Message() {
    }

    public Message(String reason, String fromdate, String todate, long timestamp, String sender) {
        this.reason = reason;
        this.fromdate = fromdate;
        this.todate = todate;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
