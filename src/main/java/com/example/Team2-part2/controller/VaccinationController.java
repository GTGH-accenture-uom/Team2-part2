package com.example.gtghTest.controller;

import com.example.gtghTest.model.Vaccination;
import com.example.gtghTest.service.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @PostMapping(path = "/vaccination")
    public String doVaccination(@RequestBody Vaccination vaccination){
        vaccinationService.getVaccinated(vaccination);
        return "Successfully vaccinated!";
    }

    @GetMapping(path = "/vaccination/all")
    public List<Vaccination> getSuccessfulVaccinations(){return vaccinationService.getVaccinationsList();}
}
