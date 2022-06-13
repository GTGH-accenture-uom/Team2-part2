package com.example.gtghTest.service;

import com.example.gtghTest.model.Timeslot;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeslotService {

    private List<Timeslot> timeslots = new ArrayList<>(); // The list of all Timeslots

    public void createTimeslot(Timeslot timeslot) { // Adds a single Timeslot on the list
        timeslots.add(timeslot);
    }

    public Timeslot getTimeslot(String date){ // Gets a single Timeslot from the list
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Timeslot newTimeslot = new Timeslot(dateTime.getDayOfMonth(),
                                            dateTime.getMonthValue(),
                                            dateTime.getYear(),
                                            dateTime.getHour(),
                                            dateTime.getMinute(),
                                            dateTime.getMinute(),
                                            dateTime.getMinute()+15,
                                            null);

        for (Timeslot timeslot: timeslots){
            if(timeslot.equals(newTimeslot)){
                return timeslot;
            }
        }
        return null;
    }

    public List<Timeslot> getEveryTimeslot(){ // Gets every Timeslot person on the list
        return timeslots;
    }

    public void importTimeslotList(List<Timeslot> timeslotList) { // Sets the Timeslot list by an imported list
        this.timeslots = timeslotList;
    }
}
