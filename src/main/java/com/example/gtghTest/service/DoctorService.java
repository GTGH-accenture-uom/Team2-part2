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

    public void assignTimeslotsToDoctor(String amka, String code) {
        if (!vaccinationCenterService.getAssignedDoctors().containsKey(getDoctor(amka))) {
            vaccinationCenterService.assignDoctorToCenter(getDoctor(amka), vaccinationCenterService.getVaccinationCenterByCode(code));
            getDoctor(amka).getTimeslots().addAll(vaccinationCenterService.getTimeslots(vaccinationCenterService.getVaccinationCenterByCode(code))); // assign 5 free timeslots to the doctor
            vaccinationCenterService.getVaccinationCenterByCode(code).getTimeslots().removeAll(vaccinationCenterService.getTimeslots(vaccinationCenterService.getVaccinationCenterByCode(code)));// then remove them from the list of the vaccination Center
            System.out.println("Successfully added timeslots to doctor!");
        }

    }

    public List<Timeslot> getTimeslots(String amka) {
        return getDoctor(amka).getTimeslots();
    }
}
