package com.example.gtghTest.service;

import com.example.gtghTest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    @Autowired
    private InsuredService insuredService;

    @Autowired
    private VaccinationCenterService centerService;

    private HashMap<Reservation, String> reservations = new HashMap<>();

    public HashMap<Reservation, String> getEveryAppointment() { // Gets every appointment on the list
        return reservations;
    } //TODO 1: THIS ONE (print in console, and save in a file named vaccination-results.txt)

    public List<Reservation> getDailyAppointments(){
        List<Reservation> theList = new ArrayList<>();

        for(Map.Entry<Reservation, String> set : reservations.entrySet()){
            if(set.getKey().getTimeslot().getYear() == LocalDateTime.now().getYear()
                    && set.getKey().getTimeslot().getMonth() == LocalDateTime.now().getMonthValue()
                    && set.getKey().getTimeslot().getDay() == LocalDateTime.now().getDayOfMonth())
            {
                theList.add(set.getKey());
            }
        }
        return theList; //TODO: (NICE TO HAVE) show the results in pages
    }

    public void reserveAnAppointment(Reservation appointment, String code) {
        for (Map.Entry<Reservation, String> set :
                reservations.entrySet()) { // if appointments list is not empty
            if (!(set.getKey().getInsured().equals(appointment.getInsured()))) {
                // checks the appointments list to see if the insured is already on there
                if (!(set.getKey().getTimeslot().equals(appointment.getTimeslot()))) {
                    // checks to see if the timeslot given is taken or not
                    // if not set the appointment for that person in that timeslot
                    set.getKey().setInsured(appointment.getInsured());
                    set.getKey().setTimeslot(appointment.getTimeslot());
                    System.out.println("Appointment created successfully!");
                } else System.out.println("This timeslot is already taken! Please try another timeslot");
            } else System.out.println("This person has already reserved an appointment!");

        }
        if (reservations.isEmpty()) { // if appointments list is empty, just add the appointment
            Reservation aReservation = new Reservation(appointment.getInsured(), appointment.getTimeslot());
            reservations.put(aReservation, code);
            System.out.println("Appointment created successfully!");
        }
    }

    public List<Insured> returnSpecificNumberOfInsured() throws IOException {
        List<Insured> theList = new ArrayList<>();
        for (Map.Entry<Reservation, String> set :
                reservations.entrySet()) {
            if (!(insuredService.getEveryInsured().contains(set.getKey().getInsured()))) {
                theList.add(set.getKey().getInsured());
                String output = "Name: " + set.getKey().getInsured().getName() + ", Surname: " +set.getKey().getInsured().getSurname() + ", AFM: " + set.getKey().getInsured().getAfm() +
                        ", AMKA: "+ set.getKey().getInsured().getAmka() + ", Birthdate : " + set.getKey().getInsured().getBirthdate() + ", email: " +set.getKey().getInsured().getEmail();
                System.out.println("Vaccinated people over the age of 60: ");
                System.out.println(output);
                SaveService.saveTofile("vaccination-results.txt", output, true);
            }
        }
        theList.removeIf(insured -> !(insured.getBirthdate().getYear() <= 1962));



        return theList; //TODO print to console and save the file
    }





    public String makeAppointment(String amka, Timeslot timeslot) {
        Reservation appointment = new Reservation(insuredService.getInsuredByAmka(amka), timeslot);

        for (Map.Entry<Doctor, VaccinationCenter> set : centerService.getAssignedDoctors().entrySet()) {
            if (set.getKey().getTimeslots().contains(timeslot)) {
                reservations.put(appointment, set.getValue().getCode());
                System.out.println("Appointment created and added to the list");
                return "Successfully created appointment";
            } else if (set.getValue().getTimeslots().contains(timeslot)) {
                System.out.println("Reservation can't be made because the timeslot is not assigned to a doctor");
                return "Couldn't add appointment to the list";
            }
        }
        return "Couldn't create an appointment";
    }

    public String changeAppointment(String amka, Timeslot timeslot, int i) {
        if (i < 2) {
            for (Map.Entry<Reservation, String> set : reservations.entrySet()) {
                if (set.getKey().getTimeslot().equals(timeslot)) {
                    return "Unable to change the appointment. The timeslot is already reserved for someone else.";
                } else if (set.getKey().getInsured().getAmka().equals(amka)) {
                    reservations.remove(set.getKey());
                    makeAppointment(amka, timeslot);
                    return "Successfuly changed the appointment.";
                }
            }
            if (reservations.isEmpty()) {
                makeAppointment(amka, timeslot);
                return "Appointment successfully created";
            }
        }
        return "You can no longer change your appointment.";
    }
}
