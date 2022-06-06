package com.example.gtghTest.controller;

import com.example.gtghTest.model.Doctor;
import com.example.gtghTest.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping(path = "/doctor") // Posts a single Doctor
    public String addDoctor(@RequestBody Doctor doctor){
        doctorService.createDoctor(doctor);
        return "Doctor created!";
    }

    @GetMapping(path = "/doctor")
    public Doctor getDoctorByAmka(@RequestParam (value = "amka") String amka){
        return doctorService.getDoctor(amka);
    }

    @PostMapping(path = "/doctor/assign-timeslot")
    public String assignTimeslotToDoctor(@RequestParam(value = "amka") String amka,
                                      @RequestParam(value = "date") String date)
    {
        doctorService.assignTimeslotToDoctor(amka, date);
        return "done!";
    }

}
