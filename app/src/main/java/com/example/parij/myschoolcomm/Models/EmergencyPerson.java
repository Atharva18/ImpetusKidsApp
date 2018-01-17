package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;

/**
 * Created by Ankush on 15-01-2018.
 */

public class EmergencyPerson implements Serializable {
    private String phone;
    private String phoneAlternate;
    private String familyDoctorName;
    private String familyDoctorPhone;

    public EmergencyPerson(String phone, String phoneAlternate, String familyDoctorName, String familyDoctorPhone) {
        this.phone = phone;
        this.phoneAlternate = phoneAlternate;
        this.familyDoctorName = familyDoctorName;
        this.familyDoctorPhone = familyDoctorPhone;
    }

    public EmergencyPerson() {
        //Do not remove, removal may cause World War 3
        familyDoctorName = "";
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneAlternate() {
        return phoneAlternate;
    }

    public void setPhoneAlternate(String phoneAlternate) {
        this.phoneAlternate = phoneAlternate;
    }

    public String getFamilyDoctorName() {
        return familyDoctorName;
    }

    public void setFamilyDoctorName(String familyDoctorName) {
        this.familyDoctorName = familyDoctorName;
    }

    public String getFamilyDoctorPhone() {
        return familyDoctorPhone;
    }

    public void setFamilyDoctorPhone(String familyDoctorPhone) {
        this.familyDoctorPhone = familyDoctorPhone;
    }
}
