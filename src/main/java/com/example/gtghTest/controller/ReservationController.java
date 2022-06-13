package com.example.gtghTest.controller;


import com.example.gtghTest.model.Reservation;
import com.example.gtghTest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping(path = "/reservation") // Posts a single Insured
    public String addReservation(@RequestBody Reservation reservation){
        reservationService.reserveAnAppointment(reservation);
        return "Reservation created!";
    }

    @GetMapping(path = "/reservation/all") // Gets every Reservation that's on the list
    public List<Reservation> getEveryReservation(){
        return reservationService.getEveryAppointment();
    }

}
