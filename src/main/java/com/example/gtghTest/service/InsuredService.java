package com.example.gtghTest.service;

import com.example.gtghTest.model.Insured;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuredService {

    private List<Insured> insuredList = new ArrayList<>(); // The list of all Insured people

    public void createInsured(Insured insured) { // Adds a single Insured on the list
        insuredList.add(insured);
    }

    public Insured getInsuredByName(String name){ // Gets a single Insured from the list by name
        for (Insured insured: insuredList){
            if(insured.getName().equals(name)){
                return insured;
            }
        }
        return null;
    }

    public Insured getInsuredByAmka(String amka){ // Gets a single Insured from the list by amka
        for (Insured insured: insuredList){
            if(insured.getAmka().equals(amka)){
                return insured;
            }
        }
        return null;
    }

    public List<Insured> getEveryInsured(){ // Gets every Insured person on the list
        return insuredList;
    }

    public void importInsuredList(List<Insured> insuredList) { // Sets the Insured list by an imported list
        this.insuredList = insuredList;
    }
}
