package com.example.gtghTest.controller;

import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.model.VaccinationCenter;
import com.example.gtghTest.service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VaccinationCenterController {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    @PostMapping(path = "/vaccination-center")
    public String createVaccinationCenter(@RequestBody VaccinationCenter vaccinationCenter){
        vaccinationCenterService.addVaccinationCenter(vaccinationCenter);
        return "Vaccination Center created!";
    }

    @PostMapping(path = "/vaccination-center/import-timeslots")
    public String importTimeslotList(@RequestBody List<Timeslot> timeslots,
                                     @RequestParam (value = "code") String code){
        vaccinationCenterService.importTimeslotList(timeslots, code);
        return "Timeslots successfully imported!";
    }

    @PostMapping(path = "/vaccination-center/add-timeslot")
    public String addTimeslotToCenter(@RequestParam(value = "code") String code,
                                      @RequestParam(value = "date") String date)
    {
        vaccinationCenterService.addTimeslotToVacCenter(code, date);
        return "Timeslot added!";
    }

    @GetMapping(path = "/vaccination-center/all-timeslots")
    public List<Timeslot> timeslots(){return vaccinationCenterService.getAllTimeslots();}
}
