package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.service.BoatFilter;
import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.entity.Photo;
import com.ajna.bookaboat.service.BoatService;
import com.ajna.bookaboat.service.BoatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/boats")
public class BoatsController {

    @Autowired
    BoatService boatService;

    @Autowired
    BoatTypeService boatTypeService;


    @GetMapping("")
    public Iterable<Boat> getAllBoats(BoatFilter boatFilter, Pageable pageable){
        return boatService.findAll(boatFilter, pageable);
    }

    @GetMapping("/{id}")
    public Boat getBoatById(@PathVariable int id){
        return  boatService.findById(id);
    }


    @PostMapping("")
    public Boat save(@RequestPart @Valid Boat boat, @RequestPart MultipartFile[] photos){
        return boatService.save(boat, photos);
    }


    @PostMapping("/boats/{boat_id}/photos")
    public List<Photo> addPhotos(@PathVariable int boat_id, @RequestPart MultipartFile[] photos){
        return boatService.addPhotos(boat_id, photos);
    }

    @GetMapping("/{id}/default-photo")
    public ResponseEntity<Resource> getDefaultPhoto(@PathVariable int id, HttpServletRequest request){
        return boatService.getDefaultPhotoFile(id, request);
    }

    @PostMapping("/{id}/default-photo/{image_name}")
    public void setDefaultPhoto(@PathVariable int id,@PathVariable String image_name){
        boatService.setPhotoAsDefault(id, image_name);
    }


    @GetMapping("/boat-types")
    public List<BoatType> getBoatTypes(){
        return boatTypeService.findAll();
    }

    @PostMapping("/boat-types")
    public BoatType saveBoatType(@RequestBody BoatType boatType){
        return boatTypeService.save(boatType);
    }

}