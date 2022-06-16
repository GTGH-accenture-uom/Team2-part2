package com.example.gtghTest.service;

import com.example.gtghTest.model.Doctor;
import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.model.VaccinationCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VaccinationCenterService {

    @Autowired
    private TimeslotService timeslotService;

    private List<VaccinationCenter> vaccinationCenters = new ArrayList<>();
    private HashMap<Doctor,VaccinationCenter> assignedDoctors = new HashMap<>();

    public void addVaccinationCenter(VaccinationCenter vaccinationCenter) {
        vaccinationCenter.getTimeslots().clear(); // Empties the timeslot list first
        if (vaccinationCenters.size() < 2)
            vaccinationCenters.add(vaccinationCenter); // Then adds it to the vaccination centers list
        else System.out.println("You can't add more vaccination centers right now");
    }

    public VaccinationCenter getVaccinationCenterByCode(String code) { // Gets a single Vaccination Center from the list
        for (VaccinationCenter vacCenter : vaccinationCenters) {
            if (vacCenter.getCode().equals(code)) {
                return vacCenter;
            }
        }
        return null;
    }

    public List<VaccinationCenter> getAllCenters() {
        return vaccinationCenters;
    } //TODO endpoint

    public void addTimeslotToVacCenter(String code, String localDateTime) {
        Timeslot t = null;
        VaccinationCenter vacCenter = getVaccinationCenterByCode(code);

        for (Timeslot timeslot : vacCenter.getTimeslots()) {
            if (timeslot.equals(timeslotService.getTimeslot(localDateTime))) {
                t = timeslot;
            }
        }
        if (t != null) {
            addTimeslot(t, vacCenter);
        }
    }

    public boolean assignDoctorToCenter(Doctor doctor, VaccinationCenter vacCenter) {
        if (!assignedDoctors.isEmpty()) {
            for(Map.Entry<Doctor, VaccinationCenter> set : assignedDoctors.entrySet()) {
                if (assignedDoctors.size()<4 && !(set.getKey().equals(doctor))) {
                    assignedDoctors.put(doctor,vacCenter);
                    return true;
                } else if (assignedDoctors.size()<4 && set.getKey().equals(doctor)) {
                    if(set.getValue().equals(vacCenter)) System.out.println("This doctor is already assigned to this center");
                    else System.out.println("This doctor is already assigned to another center");
                    return true;
                } else
                    System.out.println("All the vaccination centers are full");
                return false;
            }
        } else {
            assignedDoctors.put(doctor, vacCenter);
            return true;
        }
        return false;
    }

    public void addTimeslot(Timeslot t, VaccinationCenter vacCenter) {
        if (vacCenter.getTimeslots().size() < 10) {// checks the amount of timeslots in the list || if more than 10 doesn't add more
            t.setDoctor(null); // Sets the assigned Doctor to null
            vacCenter.getTimeslots().add(t); // Then adds the timeslot to the list
        } else System.out.println("Unable to add more than 10 timeslots!");
    }

    public Timeslot getTimeslot(VaccinationCenter vacCenter) {
        for (Timeslot timeslot : vacCenter.getTimeslots()) {
            if (timeslot.getDoctor() == null) { // checks if any of the timeslots have an assigned doctor
                return timeslot; // returns unassigned timeslot
            }
        }
        System.out.println("There are no timeslots available!");
        return null;
    }

    public void importTimeslotList(List<Timeslot> timeslots, String code) { // gets a list of timeslots
        VaccinationCenter vacCenter = getVaccinationCenterByCode(code);     // and after checking them one by one
        // adds them to the VacCenter
        for (Timeslot timeslot : timeslots) {
            timeslot.setDoctor(null);
            addTimeslot(timeslot, vacCenter); // adds every single timeslot on the list
        }
    }

    public HashMap<Doctor, VaccinationCenter> getAssignedDoctors(){return assignedDoctors;}

}
