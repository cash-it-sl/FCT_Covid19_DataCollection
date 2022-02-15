package com.example.fctc19;

public class UserHelperClass {
    String name;
    String studentNo;
    String email;
    String password1;
    String vaccinationStatus;
    String verificationStatus;
    String imageurl;

    String phoneNo;
    String imageState;
    String vaccinationState;
    public UserHelperClass() {

    }


    public String getVaccinationState() {
        return vaccinationState;
    }

    public void setVaccinationState(String vaccinationState) {
        this.vaccinationState = vaccinationState;
    }

    public UserHelperClass(String name, String studentNo, String email, String phoneNo, String password1, String vaccinationStatus, String verificationstatus, String url, String imageState, String vaccinationState) {
        this.name = name;
        this.studentNo = studentNo;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password1 = password1;
        this.vaccinationStatus = vaccinationStatus;
        this.verificationStatus = verificationstatus;
        this.imageurl = url;
        this.imageState = imageState;
        this.vaccinationState=vaccinationState;


    }

    public String getImageState() {
        return imageState;
    }

    public void setImageState(String imageState) {
        this.imageState = imageState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(String vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
