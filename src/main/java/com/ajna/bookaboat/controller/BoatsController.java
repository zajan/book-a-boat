package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.service.BoatService;
import com.ajna.bookaboat.service.BoatTypeService;
import com.ajna.bookaboat.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class BoatsController {

    @Autowired
    BoatService boatService;

    @Autowired
    BoatTypeService boatTypeService;

    @Autowired
    PhotoService photoService;

    @GetMapping("/boats")
    public List<Boat> getBoats(){
        return boatService.findAll();
    }

    @GetMapping("/boats/{id}")
    public Boat getBoatById(@PathVariable int id){
        return boatService.findById(id);
    }


    @PostMapping("/boats")
    public String save(@RequestPart Boat boat, @RequestPart MultipartFile[] photos){
        boatService.save(boat, photos);
        return "Added boat";
    }


    @PostMapping("/boats/{id}/photos")
    public String addPhotos(@PathVariable int id, @RequestPart MultipartFile[] photos){
        boatService.addPhotos(id, photos);
        return "Added photos";
    }

    @DeleteMapping("/photos/{name}")
    public String deletePhoto(@PathVariable String name){
        photoService.deleteByName(name);
        return "Photo deleted";
    }

    @GetMapping("/photos/{name}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String name, HttpServletRequest request){
        return photoService.getPhotoFileByName(name, request);
    }

    @GetMapping("/boat-types")
    public List<BoatType> getBoatTypes(){
        return boatTypeService.findAll();
    }

    @PostMapping("/boat-types")
    public String saveBoatType(@RequestBody BoatType boatType){
        boatTypeService.save(boatType);
        return "Saved boattype";
    }

}