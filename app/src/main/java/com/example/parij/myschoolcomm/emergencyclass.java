package com.example.parij.myschoolcomm;

/**
 * Created by admin on 09/11/2017.
 */

public class emergencyclass
{
    String contact;
    String alternativeContact;
    String doctorname;
    String doctorno;

    public emergencyclass()
    {

    }

    public emergencyclass(String contact,String alternativeContact,String doctorname,String doctorno)
    {
        this.contact=contact;
        this.alternativeContact=alternativeContact;
        this.doctorname=doctorname;
        this.doctorno=doctorno;

    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAlternativeContact() {
        return alternativeContact;
    }

    public void setAlternativeContact(String alternativeContact) {
        this.alternativeContact = alternativeContact;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDoctorno() {
        return doctorno;
    }

    public void setDoctorno(String doctorno) {
        this.doctorno = doctorno;
    }
}
