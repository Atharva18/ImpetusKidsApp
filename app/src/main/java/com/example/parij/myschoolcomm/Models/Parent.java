package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;

/**
 * Created by Ankush on 15-01-2018.
 */

public class Parent implements Serializable {
    private String name;
    private String phone;
    private String email;
    private int relation; //1 = Father, 2 = Mother, and 3 = Guardian
    private String imageLink;

    public Parent(String name, String phone, String email, int relation, String imageLink) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.relation = relation;
        this.imageLink = imageLink;
    }

    public Parent() {
        //Do not remove, removal may cause World War 3
        name = "";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
