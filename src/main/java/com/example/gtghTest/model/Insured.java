package com.example.gtghTest.model;

import java.time.LocalDate;

public class Insured {

    private String amka;
    private String afm;
    private String name;
    private LocalDate birthdate;
    private String surname;
    private String email;

    public Insured(String amka, String afm, String name, LocalDate birthdate, String surname, String email) {
        this.amka = amka;
        this.afm = afm;
        this.name = name;
        this.birthdate = birthdate;
        this.surname = surname;
        this.email = email;
    }

    public String getAmka() {
        return amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Insured{" +
                "amka='" + amka + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
