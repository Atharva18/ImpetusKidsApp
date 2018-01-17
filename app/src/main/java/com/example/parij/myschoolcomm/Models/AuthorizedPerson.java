package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;

/**
 * Created by Ankush on 15-01-2018.
 */

public class AuthorizedPerson implements Serializable {
    private String relation;
    private String name;
    private String phone;
    private String fromDate;
    private String toDate;
    private String imageLink;

    public AuthorizedPerson(String relation, String name, String phone, String fromDate, String toDate, String imageLink) {
        this.relation = relation;
        this.name = name;
        this.phone = phone;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.imageLink = imageLink;
    }

    public AuthorizedPerson() {
        //Do not remove, removal may cause World War 3
        name = "";
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
