package com.example.gtghTest.controller;


import com.example.gtghTest.model.Doctor;
import com.example.gtghTest.model.Insured;
import com.example.gtghTest.model.Reservation;
import com.example.gtghTest.model.Timeslot;
import com.example.gtghTest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public HashMap<Reservation, String> getEveryReservation() throws IOException {
        reservationService.printAndSaveEveryAppointment(reservationService.finalGetEveryAppointment());
        return reservationService.finalGetEveryAppointment();
    }

    @GetMapping(path = "/get-insured-old-people-with-no-reservation")
    public List<Insured> getOldPeopleList() throws IOException { return reservationService.returnSpecificNumberOfInsured();}

    @PostMapping(path = "/create-appointment")
    public String makeAppointment(@RequestParam (value = "amka") String amka,
                                  @RequestBody Timeslot timeslot){

        return reservationService.makeAppointment(amka, timeslot);
    }

    @PutMapping(path = "/update-appointment")
    public String changeAppointment(@RequestParam (value = "amka") String amka,
                                    @RequestBody Timeslot timeslot){
        int i = 0;
        return reservationService.changeAppointment(amka,timeslot,i);
    }

    @GetMapping(path = "/reservation/daily")
    public List<Reservation> returnDailyReservation() {
        return reservationService.getDailyAppointments();
    }

    @GetMapping(path = "/reservation/all-timeslots")
    public List<Timeslot> getAllFreeTimeslots() throws IOException {
        reservationService.printAndSaveAllTimeslots(reservationService.getAllTimeslots());
        return reservationService.getAllTimeslots();
    }

    @PostMapping(path = "/search-for-free-timeslot")
    public String search4Timeslot(@RequestParam(value = "day") String day,
                                  @RequestParam(value = "month") String month,
                                  @RequestParam(value = "year") String year){

        return reservationService.search4Timeslot(year,month,day);
    }
}
