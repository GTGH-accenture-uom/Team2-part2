package com.example.gtghTest.service;

import com.example.gtghTest.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    private HashMap<Reservation,String> reservations = new HashMap<>();

    //TODO: add/change a method to show the insured requested
    public HashMap<Reservation, String> getEveryAppointment(){ // Gets every appointment on the list
        return reservations;
    } //TODO 1: THIS ONE (print in console, and save in a file named vaccination-results.txt)

    public void reserveAnAppointment(Reservation appointment, String code){
        for(Map.Entry<Reservation, String> set :
                reservations.entrySet()){ // if appointments list is not empty
            if(!(set.getKey().getInsured().equals(appointment.getInsured()))){
                // checks the appointments list to see if the insured is already on there
                if(!(set.getKey().getTimeslot().equals(appointment.getTimeslot()))){
                    // checks to see if the timeslot given is taken or not
                    // if not set the appointment for that person in that timeslot
                    set.getKey().setInsured(appointment.getInsured());
                    set.getKey().setTimeslot(appointment.getTimeslot());
                    System.out.println("Appointment created successfully!");
                }
                else System.out.println("This timeslot is already taken! Please try another timeslot");
            }
            else System.out.println("This person has already reserved an appointment!");

        }
        if(reservations.isEmpty()){ // if appointments list is empty, just add the appointment
            Reservation aReservation = new Reservation(appointment.getInsured(), appointment.getTimeslot());
            reservations.put(aReservation,code);
            System.out.println("Appointment created successfully!");
        }
    }
}
