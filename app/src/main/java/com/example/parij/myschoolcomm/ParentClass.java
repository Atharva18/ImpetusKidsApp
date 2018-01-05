package com.example.parij.myschoolcomm;

/**
 * Created by admin on 03/11/2017.
 */

public class ParentClass
{
    String fathername;
    String mothername;
    String occupation;
    String contactno;
    String emailid;
    String qualification;
    String officeno;
    String city;
    String street;
    String landmark;
    String pincode;

    public ParentClass(String fathername, String mothername, String occupation, String contactno, String emailid, String qualification, String officeno, String city, String street, String landmark, String pincode) {
        this.fathername = fathername;
        this.mothername = mothername;
        this.occupation = occupation;
        this.contactno = contactno;
        this.emailid = emailid;
        this.qualification = qualification;
        this.officeno = officeno;
        this.city = city;
        this.street = street;
        this.landmark = landmark;
        this.pincode = pincode;
    }

    public ParentClass()
    {

    }

    public String getFathername() {
        return fathername;
    }

    public String getMothername() {
        return mothername;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getContactno() {
        return contactno;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getQualification() {
        return qualification;
    }

    public String getOfficeno() {
        return officeno;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getPincode() {
        return pincode;
    }
}
