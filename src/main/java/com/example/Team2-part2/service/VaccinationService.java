package com.example.gtghTest.service;

import com.example.gtghTest.model.Vaccination;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccinationService {

    private List<Vaccination> successfulVaccinations = new ArrayList<>();

    public void getVaccinated(Vaccination vaccination) {
        successfulVaccinations.add(vaccination);
    }

    public List<Vaccination> getVaccinationsList(){return successfulVaccinations;}
}
