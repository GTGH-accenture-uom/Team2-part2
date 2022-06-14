package com.example.gtghTest.service;

import com.example.gtghTest.model.Insured;
import com.example.gtghTest.model.Reservation;
import com.example.gtghTest.model.Vaccination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VaccinationService {

    @Autowired
    private ReservationService reservationService;

    private List<Vaccination> successfulVaccinations = new ArrayList<>();

    public void getVaccinated(Vaccination vaccination) {
        for(Map.Entry<Reservation, String> set :reservationService.getEveryAppointment().entrySet()){ // checks to see if the Insured has an appointment first
            if(set.getKey().getInsured().equals(vaccination.getInsured()))
                successfulVaccinations.add(vaccination); // if yes vaccinate them
            else System.out.println("This person can't get vaccinated, because they don't have an appointment"); // if not then don't
        }

    }

    public List<Vaccination> getVaccinationsList(){return successfulVaccinations;} //TODO 3: (print in console, and save in a file named vaccination-results.txt)
}
