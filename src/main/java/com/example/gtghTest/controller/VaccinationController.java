package com.example.gtghTest.controller;

import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.model.Vaccination;
import com.example.gtghTest.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @PostMapping(path = "/vaccination")
    public String doVaccination(@RequestBody Timeslot timeslot,
                                @RequestParam (value = "amka") String amka,
                                @RequestParam (value = "expDate") String expDate){
        vaccinationService.getVaccinated(timeslot, amka, expDate);
        vaccinationService.getVaccinatedInsured();
        return "Successfully vaccinated!";
    }

    @GetMapping(path = "/vaccination/all")
    public List<Vaccination> getSuccessfulVaccinations() throws IOException {return vaccinationService.getVaccinationsList();}

    @GetMapping(path = "/vaccination/status")
    public Vaccination getStatus(@RequestParam(value = "amka") String amka){
        return vaccinationService.getStatus(amka);
    }
}
