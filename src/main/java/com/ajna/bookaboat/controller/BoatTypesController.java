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
        boatType.setId(0);
        return boatTypeService.save(boatType);
    }

    @PutMapping("/{id}")
    public BoatType updateBoatType(@PathVariable int id, @RequestBody BoatType boatType){
        boatType.setId(id);
        return boatTypeService.save(boatType);
    }

    @DeleteMapping("/{id}")
    public void deleteBoatTypeById(@PathVariable int id){
        boatTypeService.deleteById(id);
    }

}
