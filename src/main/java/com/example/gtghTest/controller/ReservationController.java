package com.example.gtghTest.controller;


import com.example.gtghTest.model.Reservation;
import com.example.gtghTest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping(path = "/reservation") // Posts a single Reservation
    public String addReservation(@RequestBody Reservation reservation,
                                 @RequestParam (value = "code") String code){
        reservationService.reserveAnAppointment(reservation, code);
        return "Reservation created!";
    }

    @GetMapping(path = "/reservation/all") // Gets every Reservation that's on the list
    public HashMap<Reservation, String> getEveryReservation(){
        return reservationService.getEveryAppointment();
    }

}
