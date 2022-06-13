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

    public Insured getInsured(String name){ // Gets a single Insured from the list
        for (Insured insured: insuredList){
            if(insured.getName().equals(name)){
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
