package com.example.gtghTest.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String amka;
    private String name;
    private String surname;
    private List<Timeslot> timeslots;

    public Doctor(String amka, String name, String surname, List<Timeslot> timeslots) {
        this.amka = amka;
        this.name = name;
        this.surname = surname;
        this.timeslots = timeslots;
    }

    public String getAmka() {
        return amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
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

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
    }

    public void assignTimeslot(Timeslot timeslot){
        if(timeslots.size()<5){
            timeslots.add(timeslot);
        }
        else System.out.println("The doctor has already reached the max capacity of assigned timeslots!");
    }
}
