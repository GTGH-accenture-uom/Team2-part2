package com.example.gtghTest.model;

import java.util.ArrayList;
import java.util.List;

public class VaccinationCenter {

    private String code;
    private String address;
    private List<Timeslot> unassignedTimeslots;

    public VaccinationCenter(String code, String address, List<Timeslot> timeslots) {
        this.code = code;
        this.address = address;
        this.unassignedTimeslots = timeslots;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Timeslot> getTimeslots() {
        return unassignedTimeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.unassignedTimeslots = timeslots;
    }

}
