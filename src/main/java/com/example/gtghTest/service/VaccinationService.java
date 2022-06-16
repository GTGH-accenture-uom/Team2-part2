package com.example.gtghTest.service;

import com.example.gtghTest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VaccinationService {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private VaccinationCenterService centerService;

    private List<Vaccination> successfulVaccinations = new ArrayList<>();
    private static Timeslot t;

    public void getVaccinated(Timeslot timeslot, String amka, String expDate) {
        Vaccination vaccination;
        Doctor doctor = null;

        List<Timeslot> docTimeslots;
        for (Map.Entry<Doctor, VaccinationCenter> docSet : centerService.getAssignedDoctors().entrySet()) {
            docTimeslots = docSet.getKey().getTimeslots();
            for (Timeslot t1 : docTimeslots) {
                if (t1.getYear() == timeslot.getYear() && t1.getMonth() == timeslot.getMonth() && t1.getDay() == timeslot.getDay()
                        && t1.getHour() == timeslot.getHour() && t1.getMinutes() == timeslot.getMinutes()) {
                    doctor = docSet.getKey();
                    t = t1;
                    break;
                }
            }
        }
        for (Map.Entry<Reservation, String> set : reservationService.getEveryAppointment().entrySet()) {// checks to see if the Insured has an appointment first
            if (set.getKey().getTimeslot().equals(t)) {
                vaccination = new Vaccination(set.getKey().getInsured(), doctor,
                        set.getKey().getTimeslot().getYear() + " " +
                                set.getKey().getTimeslot().getMonth() + " " +
                                set.getKey().getTimeslot().getDay(), expDate);
                successfulVaccinations.add(vaccination); // if yes vaccinate them
            } else
                System.out.println("This person can't get vaccinated, because they don't have an appointment"); // if not then don't
        }
    }

    public void subtractReservation() {
        for (Map.Entry<Reservation, String> set : reservationService.getEveryAppointment().entrySet()) {
            if (set.getKey().getTimeslot().equals(t))
                reservationService.getEveryAppointment().remove(set.getKey());
        }
    }

    public List<Vaccination> getVaccinationsList() throws IOException {
        for (Vaccination vaccination : successfulVaccinations) {
            String output = "Doctor: " + vaccination.getDoctor().getSurname() + ", doctor amka: " + vaccination.getDoctor().getAmka() + ", Insured: " +
                    vaccination.getInsured().getName() + ", Surname: " + vaccination.getInsured().getSurname() + ", Vaccination date: " + vaccination.getVaccinationDate();
            System.out.println("Vaccinations for each doctor: ");
            System.out.println(output);
            SaveService.saveTofile("vaccination-results.txt", output, true);
        }
        return successfulVaccinations;
    }

    public Vaccination getStatus(String amka) {
        for (Vaccination vaccination : successfulVaccinations) {
            if (vaccination.getInsured().getAmka().equals(amka)) {
                System.out.println("Vaccination status checked.");
                return vaccination;
            }
        }
        System.out.println("Can't check status because vaccination is not complete");
        return null;

        //TODO: (NICE TO HAVE) could add potential QR check
    }
}
