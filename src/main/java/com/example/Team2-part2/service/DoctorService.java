package com.example.gtghTest.service;

import com.example.gtghTest.model.Doctor;
import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.model.VaccinationCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    private List<Doctor> doctors = new ArrayList<>();
    public void createDoctor(Doctor doctor) {
        if(doctors.size()<4) doctors.add(doctor);
        else System.out.println("You can't add any more doctors!");
    }

    public Doctor getDoctor(String amka) {
        for (Doctor doctor: doctors){
            if(doctor.getAmka().equals(amka)){
                return doctor;
            }
        }
        return null;
    }

    public void assignTimeslotToDoctor(String amka, String date){
        Timeslot t = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        for(VaccinationCenter vacCenter: vaccinationCenterService.getAllCenters()){ // searches every center for an empty timeslot
            t = vaccinationCenterService.getTimeslot(vacCenter);
        }
        if(t!=null){
            for (Doctor doctor: doctors){
                if (doctor.getAmka().equals(amka) && vaccinationCenterService.assignDoctorToCenter(doctor)) {
                    // checks to see if the doctor we are trying to assign to the center's timeslot is assigned to that center
                    // if not and there's space add him, else he is not assigned
                    doctor.assignTimeslot(t);
                }
                else System.out.println("Not able to assign this timeslot to the Doctor!");
            }
        }
    }

}
