package com.example.nevzat.semesterproject.models;


/**
 * Created by nevzat on 18/12/15.
 */
public class Person {
    private String name;
    private String surname;
    private Phone phone;
    private String eMail;
    private Location location;
    private ActivityStatistic statistic;

    public Person() {
    }

    public Person(String name, String surname, Phone phone, String eMail, Location location) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eMail = eMail;
        this.location = location;
    }

    public Person(String name, String surname, Phone phone, String eMail, Location location, ActivityStatistic statistic) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eMail = eMail;
        this.location = location;
        this.statistic = statistic;
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

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ActivityStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(ActivityStatistic statistic) {
        this.statistic = statistic;
    }
}
