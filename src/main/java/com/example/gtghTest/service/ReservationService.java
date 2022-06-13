package com.example.gtghTest.service;

import com.example.gtghTest.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getEveryAppointment(){ // Gets every Insured person on the list
        return reservations;
    }

    public void reserveAnAppointment(Reservation appointment){
        for(Reservation reservation : reservations){ // if appointments list is not empty
            if(!(reservation.getInsured().equals(appointment.getInsured()))){
                // checks the appointments list to see if the insured is already on there
                if(!(reservation.getTimeslot().equals(appointment.getTimeslot()))){
                    // checks to see if the timeslot given is taken or not
                    // if not set the appointment for that person in that timeslot
                    reservation.setInsured(appointment.getInsured());
                    reservation.setTimeslot(appointment.getTimeslot());
                    System.out.println("Appointment created successfully!");
                }
                else System.out.println("This timeslot is already taken! Please try another timeslot");
            }
            else System.out.println("This person has already reserved an appointment!");

        }
        if(reservations.isEmpty()){ // if appointments list is empty, just add the appointment
            Reservation aReservation = new Reservation(appointment.getInsured(), appointment.getTimeslot());
            reservations.add(aReservation);
            System.out.println("Appointment created successfully!");
        }
    }
}
