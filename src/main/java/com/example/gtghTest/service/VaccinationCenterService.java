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
public class VaccinationCenterService {

    @Autowired
    private TimeslotService timeslotService;

    private List<VaccinationCenter> vaccinationCenters = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();

    public void addVaccinationCenter(VaccinationCenter vaccinationCenter) {
        vaccinationCenters.add(vaccinationCenter);
    }

    public VaccinationCenter getVaccinationCenterByCode(String code){ // Gets a single Vaccination Center from the list
        for (VaccinationCenter vacCenter: vaccinationCenters){
            if(vacCenter.getCode().equals(code)){
                return vacCenter;
            }
        }
        return null;
    }

    public List<VaccinationCenter> getAllCenters(){
       return vaccinationCenters;
    }

    public void addTimeslotToVacCenter(String code, String localDateTime) {
        Timeslot t = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(localDateTime, formatter);

        for(Timeslot timeslot: timeslotService.getEveryTimeslot()){
            if(timeslot.getLocalDateTime().equals(dateTime)){
                t = timeslot;
            }
        }
        if(t!=null){
            for (VaccinationCenter vaccinationCenter: vaccinationCenters){
                if (vaccinationCenter.getCode().equals(code)){
                    addTimeslot(t, vaccinationCenter);
                }
            }
        }
    }

    public boolean assignDoctorToCenter(Doctor doctor){
        for(Doctor doc : doctors){
           if(doctors.size()<2 && !(doc.equals(doctor))){
               doctors.add(doctor);
               return true;
           }
           else System.out.println("This doctor is already assigned here, or there are too many doctors already assigned!");
           return false;

        }
        return false;
    }

    public void addTimeslot(Timeslot t, VaccinationCenter vacCenter) {
        if(vacCenter.getTimeslots().size()<10){ // checks the amount of timeslots in the list || if more than 10 doesn't add more
            vacCenter.getTimeslots().add(t);
        }
        else System.out.println("Unable to add more than 10 timeslots!");
    }

    public Timeslot getTimeslot(VaccinationCenter vacCenter){
        for(Timeslot timeslot : vacCenter.getTimeslots()){
            if(timeslot.getDoctor()==null) // checks if any of the timeslots have an assigned doctor
                return timeslot; // returns unassigned timeslot
        }
        System.out.println("There are no timeslots available!");
        return null;
    }

}
