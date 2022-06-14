package com.example.gtghTest.controller;

import com.example.gtghTest.model.Insured;
import com.example.gtghTest.service.InsuredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsuredController {

    @Autowired
    private InsuredService insuredService;

    @PostMapping(path = "/insured") // Posts a single Insured
    public String addInsured(@RequestBody Insured insured){
        insuredService.createInsured(insured);
        return "Insured created!";
    }

    @PostMapping(path = "/insured/all") // Imports a list of Insured from a JSON file
    public String importInsured(@RequestBody List<Insured> insuredList){
        insuredService.importInsuredList(insuredList);
        return "Insured list successfully imported!";
    }

    @GetMapping(path = "/insured-by-name") // Gets a specific Insured by their name
    public Insured getSpecificInsuredByName(@RequestParam(value = "name") String name){
        return insuredService.getInsuredByName(name);
    }

    @GetMapping(path = "/insured") // Gets a specific Insured by their name
    public Insured getSpecificInsuredByAmka(@RequestParam(value = "amka") String amka){
        return insuredService.getInsuredByAmka(amka);
    }

    @GetMapping(path = "/insured/all") // Gets every Insured that's on the list
    public List<Insured> getEveryInsured(){
        return insuredService.getEveryInsured();
    }
}
