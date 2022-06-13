package com.example.gtghTest.controller;

import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.service.TimeslotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TimeslotController {

    @Autowired
    private TimeslotService timeslotService;

    @PostMapping(path = "/timeslot") // Posts a single Timeslot
    public String addTimeslot(@RequestBody Timeslot timeslot){
        timeslotService.createTimeslot(timeslot);
        return "Timeslot created!";
    }

    @PostMapping(path = "/timeslot/all") // Imports a list of Timeslot from a JSON file
    public String importTimeslot(@RequestBody List<Timeslot> TimeslotList){
        timeslotService.importTimeslotList(TimeslotList);
        return "Timeslot list successfully imported!";
    }

    @GetMapping(path = "/timeslot") // Gets a specific Timeslot by their name
    public Timeslot getSpecificTimeslotByName(@RequestParam(value = "localDate") String date){
        return timeslotService.getTimeslot(date);
    }

    @GetMapping(path = "/timeslot/all") // Gets every Timeslot that's on the list
    public List<Timeslot> getEveryTimeslot(){
        return timeslotService.getEveryTimeslot();
    }
}
