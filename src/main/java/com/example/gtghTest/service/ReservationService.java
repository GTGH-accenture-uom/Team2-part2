package com.example.gtghTest.service;

import com.example.gtghTest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    List<Insured> theVaccinatedInsuredList = new ArrayList<>();

    public HashMap<Reservation, String> getEveryAppointment(){ // Gets every appointment on the list
        return reservations;
    }

    public HashMap<Reservation, String> finalGetEveryAppointment(){
        HashMap<Reservation,String> ourList = new HashMap<>();
        for(Map.Entry<Reservation,String> set : reservations.entrySet()){
            if(!theVaccinatedInsuredList.contains(set.getKey().getInsured()))
                ourList.put(set.getKey(),set.getValue());
        }
        return ourList;
    }

    public void printAndSaveEveryAppointment(HashMap<Reservation, String> appointments) throws IOException {
        for (Map.Entry<Reservation, String> set : appointments.entrySet()) {
            String output = "Name: " + set.getKey().getInsured().getName() + ", Surname: " + set.getKey().getInsured().getSurname() + ", AMKA: " + set.getKey().getInsured().getAmka() +
                    ", Timeslot: " + set.getKey().getTimeslot().toString();
            System.out.println("Upcoming Appointments per Vaccination Center: ");
            System.out.println(output);
            SaveService.saveTofile("vaccination-results.txt", output, true);
        }
    }

    public List<Reservation> getSpecificDateAppointments(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);

        List<Reservation> theList = new ArrayList<>();

        for (Map.Entry<Reservation, String> set : reservations.entrySet()) {
            if (set.getKey().getTimeslot().getYear() == dateTime.getYear()
                    && set.getKey().getTimeslot().getMonth() == dateTime.getMonthValue()
                    && set.getKey().getTimeslot().getDay() == dateTime.getDayOfMonth()) {
                theList.add(set.getKey());
            }
        }
        return theList; //TODO: (NICE TO HAVE) show the results in pages
    }

    public void reserveAnAppointment(Reservation appointment, String code) {
        for (Map.Entry<Reservation, String> set : reservations.entrySet()) { // if appointments list is not empty
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
                String output = "Name: " + set.getKey().getInsured().getName() + ", Surname: " + set.getKey().getInsured().getSurname() + ", AFM: " + set.getKey().getInsured().getAfm() +
                        ", AMKA: " + set.getKey().getInsured().getAmka() + ", Birthdate : " + set.getKey().getInsured().getBirthdate() + ", email: " + set.getKey().getInsured().getEmail();
                System.out.println("Vaccinated people over the age of 60: ");
                System.out.println(output);
                SaveService.saveTofile("vaccination-results.txt", output, true);
            }
        }
        theList.removeIf(insured -> !(insured.getBirthdate().getYear() <= 1962));

        return theList;
    }

    public String makeAppointment(String amka, Timeslot timeslot) {
        Reservation appointment;

        for (Map.Entry<Doctor, VaccinationCenter> set : centerService.getAssignedDoctors().entrySet()) {
            for (Timeslot t : set.getKey().getTimeslots()) {
                if (t.getYear()==timeslot.getYear() && t.getMonth()==timeslot.getMonth() && t.getDay()==timeslot.getDay()
                    && t.getHour()==timeslot.getHour() && t.getMinutes()==timeslot.getMinutes()) {
                    if(reservations.isEmpty()){
                        appointment = new Reservation(insuredService.getInsuredByAmka(amka), t);
                        reservations.put(appointment,set.getValue().getCode());
                        System.out.println("Appointment created and added to the list");
                        return "Successfully created appointment!";
                    }
                    for(Map.Entry<Reservation, String> otherSet : reservations.entrySet()){
                        if(!otherSet.getKey().getTimeslot().equals(t)){
                            appointment = new Reservation(insuredService.getInsuredByAmka(amka),t);
                            reservations.put(appointment,set.getValue().getCode());
                            System.out.println("Appointment created and added to the list");
                            return "Successfully created appointment!";
                        }
                        else{
                            System.out.println("Appointment not created");
                            return "Can't create appointment. The timeslot is already reserved for someone else.";
                        }
                    }
                } else if (set.getValue().getTimeslots().contains(t)) {
                    System.out.println("Reservation can't be made because the timeslot is not assigned to a doctor");
                    return "Couldn't add appointment to the list";
                }
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

    public List<Timeslot> getAllTimeslots() { // Shows the free timeslots of every vaccination center
        List<Timeslot> freeTimeslots = new ArrayList<>();

        for (Map.Entry<Doctor, VaccinationCenter> set : centerService.getAssignedDoctors().entrySet()) {// Search Doctors' list for assigned timeslots
            freeTimeslots.addAll(set.getKey().getTimeslots());
            for (Map.Entry<Reservation, String> set2 : reservations.entrySet()) { // Search the reservation list for occupied timeslots
                if (freeTimeslots.contains(set2.getKey().getTimeslot())) { // Compare the sets to see if there are any free timeslots
                    freeTimeslots.remove(set2.getKey().getTimeslot()); // If there's an occupied timeslot remove it from the list
                }
            }
        }
        return freeTimeslots;
    }

    public void printAndSaveAllTimeslots(List<Timeslot> theList) throws IOException {
        for (Timeslot t : theList) {
            System.out.println(t.toString());
            SaveService.saveTofile("vaccination-results.txt", t.toString(), true);
        }
    }

    public String search4Timeslot(String year, String month, String day) {
        // Searches the timeslot given in the free timeslots' list by day
        for (Timeslot t : getAllTimeslots()) {
            if (t.getYear() == Integer.parseInt(year) && t.getMonth() == Integer.parseInt(month) && t.getDay() == Integer.parseInt(day)) {
                System.out.println("Timeslots found");
                return "Available timeslots found.";
            }
        }
        System.out.println("No timeslots found");
        return "No available slots found on this date.";
    }

    public String search4Timeslot(String year, String month) {
        // Searches the timeslot given in the free timeslots' list by month
        for (Timeslot t : getAllTimeslots()) {
            if (t.getYear() == Integer.parseInt(year) && t.getMonth() == Integer.parseInt(month)) {
                System.out.println("Timeslots found");
                return "Available timeslots found.";
            }
        }
        System.out.println("No timeslots found");
        return "No available slots found on this date.";
    }

    public void getVaccinatedInsuredList(List<Insured> vaccinatedInsuredList){
        theVaccinatedInsuredList = vaccinatedInsuredList;
    }
}
