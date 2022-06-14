package com.example.gtghTest.service;

import com.example.gtghTest.model.Doctor;
import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.model.VaccinationCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    private List<Doctor> doctors = new ArrayList<>();

    public void createDoctor(Doctor doctor) {
        if (doctors.size() < 4) {
            doctor.getTimeslots().clear(); // When the Doctor is first created they shouldn't have any timeslots assigned to them
            doctors.add(doctor);
        } else System.out.println("You can't add any more doctors!");
    }

    public Doctor getDoctor(String amka) {
        for (Doctor doctor : doctors) {
            if (doctor.getAmka().equals(amka)) {
                return doctor;
            }
        }
        return null;
    }

    public void assignTimeslotsToDoctor(String amka) {
        Timeslot t;

        for (VaccinationCenter vacCenter : vaccinationCenterService.getAllCenters()) { // searches every center for an empty timeslot
            t = vaccinationCenterService.getTimeslot(vacCenter);

            if (t != null) {
                if (vaccinationCenterService.assignDoctorToCenter(getDoctor(amka),vacCenter)) {
                    // checks to see if the doctor we are trying to assign to the center's timeslot is assigned to that center
                    // if not and there's space add them, else he is not assigned
                    getDoctor(amka).assignTimeslot(t); // assign the free timeslot to the doctor
                    if (getDoctor(amka).getTimeslots().contains(t)) vacCenter.getTimeslots().remove(t); // then remove it from the list of the vaccination Center
                } else System.out.println("Not able to assign this timeslot to the Doctor!");
            }
        }
    }

}
