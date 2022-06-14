package com.example.gtghTest.service;

import com.example.gtghTest.model.Insured;
import com.example.gtghTest.model.Reservation;
import com.example.gtghTest.model.Vaccination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationService {

    @Autowired
    private ReservationService reservationService;

    private List<Vaccination> successfulVaccinations = new ArrayList<>();

    public void getVaccinated(Vaccination vaccination) {
        for(Reservation appointment : reservationService.getEveryAppointment()){ // checks to see if the Insured has an appointment first
            if(appointment.getInsured().equals(vaccination.getInsured()))
                successfulVaccinations.add(vaccination); // if yes vaccinate them
            else System.out.println("This person can't get vaccinated, because they don't have an appointment"); // if not then don't
        }

    }

    public List<Vaccination> getVaccinationsList(){return successfulVaccinations;} //TODO: change the method to show the info requested
}
