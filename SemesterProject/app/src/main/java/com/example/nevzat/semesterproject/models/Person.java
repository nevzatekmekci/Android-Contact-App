package com.example.nevzat.semesterproject.models;


import java.util.ArrayList;

/**
 * Created by nevzat on 18/12/15.
 */
public class Person {

    private String pid;
    private String name;
    private String surname;
    private ArrayList<Phone> phone;
    private String eMail;
    private ArrayList<Location> location;
    private ActivityStatistic statistic;

    public Person() {
    }

    public Person(String name, String surname, ArrayList<Phone> phone, String eMail, ArrayList<Location> location) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eMail = eMail;
        this.location = location;
        this.statistic = null;
    }

    public Person(String name, String surname, ArrayList<Phone> phone, String eMail, ArrayList<Location> location, ActivityStatistic statistic) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eMail = eMail;
        this.location = location;
        this.statistic = statistic;
    }
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public ArrayList<Phone> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<Phone> phone) {
        this.phone = phone;
    }

    public ArrayList<Location> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<Location> location) {
        this.location = location;
    }

    public ActivityStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(ActivityStatistic statistic) {
        this.statistic = statistic;
    }
}
