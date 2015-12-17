package com.example.nevzat.semesterproject.models;

/**
 * Created by nevzat on 18/12/15.
 */
public class Phone {
    private String phoneNumber;
    private PhoneType phoneType;

    public Phone(String phoneNumber,PhoneType phoneType){
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    public Phone(){

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }
}
