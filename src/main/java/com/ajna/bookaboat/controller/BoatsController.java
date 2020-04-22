package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.exception.DummyException;
import com.ajna.bookaboat.service.BoatService;
import com.ajna.bookaboat.service.BoatTypeService;
import com.ajna.bookaboat.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
public class BoatsController {

    @Autowired
    BoatService boatService;

    @Autowired
    BoatTypeService boatTypeService;

    @Autowired
    PhotoService photoService;

    @GetMapping("/boats")
    public List<Boat> getAllBoats(){
        return boatService.findAll();
    }

    @GetMapping(value = "/boats", params = {"page", "number"})
    public Iterable<Boat> getBoats(@RequestParam int page, @RequestParam int number){
        return boatService.findForPage(PageRequest.of(page, number));
    }

    @GetMapping("/boats/{id}")
    public Boat getBoatById(@PathVariable int id){
        return  boatService.findById(id);
    }


    @PostMapping("/boats")
    public Boat save(@RequestPart @Valid Boat boat, @RequestPart MultipartFile[] photos){

        return boatService.save(boat, photos);
    }


    @PostMapping("/boats/{boat_id}/photos")
    public String addPhotos(@PathVariable int boat_id, @RequestPart MultipartFile[] photos){
        boatService.addPhotos(boat_id, photos);
        return "Added photos";
    }

    @GetMapping("/boats/{id}/default-photo")
    public ResponseEntity<Resource> getDefaultPhoto(@PathVariable int id, HttpServletRequest request){
        return boatService.getDefaultPhotoFile(id, request);
    }

    @PostMapping("/boats/{id}/default-photo/{image_name}")
    public String setDefaultPhoto(@PathVariable int id,@PathVariable String image_name){
        boatService.setPhotoAsDefault(id, image_name);
        return "Default photo is set";
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