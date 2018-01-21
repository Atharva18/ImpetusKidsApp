package com.example.parij.myschoolcomm.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ankush on 12-01-2018.
 */

public class Student implements Serializable {
    private String rollNo;
    private String name;
    private String username;
    private String password;
    private String admissionDate;
    private int program;
    private int batch;
    private int gender;
    private String dateOfBirth;
    private int bloodGroup;
    private String classTeacherName;
    private String classTeacherPhone;
    private String imageLink;
    private ArrayList<Memory> memoryImageLinks = new ArrayList<>(9);
    private Parent father, mother, guardian;
    private AuthorizedPerson authorizedPerson;
    private EmergencyPerson emergencyPerson;

    public Student(String rollNo, String name, String username, String password, String admissionDate, int program, int batch, int gender, String dateOfBirth, int bloodGroup, String classTeacherName, String classTeacherPhone, String imageLink, ArrayList<Memory> memoryImageLinks, Parent father, Parent mother, Parent guardian, AuthorizedPerson authorizedPerson, EmergencyPerson emergencyPerson) {
        this.rollNo = rollNo;
        this.name = name;
        this.username = username;
        this.password = password;
        this.admissionDate = admissionDate;
        this.program = program;
        this.batch = batch;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.classTeacherName = classTeacherName;
        this.classTeacherPhone = classTeacherPhone;
        this.imageLink = imageLink;
        this.memoryImageLinks = memoryImageLinks;
        this.father = father;
        this.mother = mother;
        this.guardian = guardian;
        this.authorizedPerson = authorizedPerson;
        this.emergencyPerson = emergencyPerson;
    }

    public Student() {
        //Do not remove, removal may cause World War 3
        name = "";
        rollNo = "";
        username = "";
        password = "";
        admissionDate = "";
        program = -1;
        batch = -1;
        gender = -1;
        dateOfBirth = "";
        bloodGroup = -1;
        classTeacherName = "";
        classTeacherPhone = "";
        imageLink = "";
        authorizedPerson = new AuthorizedPerson();
        emergencyPerson = new EmergencyPerson();
        father = new Parent();
        mother = new Parent();
        guardian = new Parent();

    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public int getProgram() {
        return program;
    }

    public void setProgram(int program) {
        this.program = program;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(int bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

    public String getClassTeacherPhone() {
        return classTeacherPhone;
    }

    public void setClassTeacherPhone(String classTeacherPhone) {
        this.classTeacherPhone = classTeacherPhone;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ArrayList<Memory> getMemoryImageLinks() {
        return memoryImageLinks;
    }

    public void setMemoryImageLinks(ArrayList<Memory> memoryImageLinks) {
        this.memoryImageLinks = memoryImageLinks;
    }

    public Parent getFather() {
        return father;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getMother() {
        return mother;
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public Parent getGuardian() {
        return guardian;
    }

    public void setGuardian(Parent guardian) {
        this.guardian = guardian;
    }

    public AuthorizedPerson getAuthorizedPerson() {
        return authorizedPerson;
    }

    public void setAuthorizedPerson(AuthorizedPerson authorizedPerson) {
        this.authorizedPerson = authorizedPerson;
    }

    public EmergencyPerson getEmergencyPerson() {
        return emergencyPerson;
    }

    public void setEmergencyPerson(EmergencyPerson emergencyPerson) {
        this.emergencyPerson = emergencyPerson;
    }
}
