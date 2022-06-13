package com.example.gtghTest.controller;

import com.example.gtghTest.model.VaccinationCenter;
import com.example.gtghTest.service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VaccinationCenterController {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    @PostMapping(path = "/vaccination-center")
    public String createVaccinationCenter(@RequestBody VaccinationCenter vaccinationCenter){
        vaccinationCenterService.addVaccinationCenter(vaccinationCenter);
        return "Vaccination Center created!";
    }

    @PostMapping(path = "/vaccination-center/add-timeslot")
    public String addTimeslotToCenter(@RequestParam(value = "code") String code,
                                      @RequestParam(value = "date") String date)
    {
        vaccinationCenterService.addTimeslotToVacCenter(code, date);
        return "done!";
    }
}
