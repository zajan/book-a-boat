package com.ajna.bookaboat.controller;


import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.service.BoatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/boat-types")
public class BoatTypesController {

    @Autowired
    private BoatTypeService boatTypeService;

    @GetMapping("")
    public List<BoatType> getBoatTypes(){
        return boatTypeService.findAll();
    }

    @PostMapping("")
    public BoatType saveBoatType(@RequestBody BoatType boatType){
        return boatTypeService.save(boatType);
    }

}
