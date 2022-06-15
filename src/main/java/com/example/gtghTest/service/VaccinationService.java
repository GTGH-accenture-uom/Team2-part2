package com.example.gtghTest.service;

import com.example.gtghTest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VaccinationService {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VaccinationCenterService centerService;

    private List<Vaccination> successfulVaccinations = new ArrayList<>();

    public void getVaccinated(Timeslot timeslot, String amka, String expDate) throws IOException {
        Vaccination vaccination = null;
        Doctor doctor = null;
        for(Map.Entry<Doctor, VaccinationCenter> docSet : centerService.getAssignedDoctors().entrySet()){
            if (docSet.getKey().getTimeslots().contains(timeslot)) doctor = docSet.getKey();
        }

        for (Map.Entry<Reservation, String> set : reservationService.getEveryAppointment().entrySet()) { // checks to see if the Insured has an appointment first
            if (set.getKey().getInsured().getAmka().equals(amka)) {
                vaccination = new Vaccination(set.getKey().getInsured(), doctor,
                        set.getKey().getTimeslot().getYear() + " " +
                                set.getKey().getTimeslot().getMonth() + " " +
                                set.getKey().getTimeslot().getDay(), expDate);
                successfulVaccinations.add(vaccination); // if yes vaccinate them
            }
            else
                System.out.println("This person can't get vaccinated, because they don't have an appointment"); // if not then don't
        }

    }

    public List<Vaccination> getVaccinationsList() {
        return successfulVaccinations;
    } //TODO 3: (print in console, and save in a file named vaccination-results.txt)

    public Vaccination getStatus(String amka) {
        for (Vaccination vaccination : successfulVaccinations) {
            if (vaccination.getInsured().getAmka().equals(amka)) {
                System.out.println("Vaccination status checked.");
                return vaccination;
            }
        }
        System.out.println("Can't check status because vaccination is not complete");
        return null; //TODO: (NICE TO HAVE) could add potential QR check
    }
}
