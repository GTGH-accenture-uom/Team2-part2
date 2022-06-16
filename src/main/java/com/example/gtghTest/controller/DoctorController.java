package com.example.gtghTest.controller;

import com.example.gtghTest.model.Doctor;
import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/doctor/timeslots")
    public List<Timeslot> getDoctorTimeslots(@RequestParam(value = "amka") String amka){
        return doctorService.getTimeslots(amka);
    }

    @PostMapping(path = "/doctor/assign-timeslot")
    public String assignTimeslotsToDoctor(@RequestParam(value = "amka") String amka,
                                          @RequestParam(value = "code") String code) {
        doctorService.assignTimeslotsToDoctor(amka,code);
        return "Timeslots assigned to the doctor";
    }

}
