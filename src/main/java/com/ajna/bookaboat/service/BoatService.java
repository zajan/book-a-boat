package com.ajna.bookaboat.service;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.Photo;
import com.ajna.bookaboat.respository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BoatService {

    @Autowired
    BoatRepository boatRepository;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    PhotoService photoService;


    public List<Boat> findAll() {
        return boatRepository.findAll();
    }

    public Boat findById(int id) {
        Boat boat = null;
        Optional<Boat> optBoat = boatRepository.findById(id);
        if (optBoat.isPresent()) {
            boat = optBoat.get();
        }
        return boat;
    }

    public Boat findByName(String name) {
        Boat boat = null;
        Optional<Boat> optBoat = boatRepository.findByName(name);
        if (optBoat.isPresent()) {
            boat = optBoat.get();
        }
        return boat;
    }

    public void save(Boat boat) {
        boatRepository.save(boat);
    }

    public void save(Boat boat, MultipartFile[] photos) {

        boatRepository.save(boat);
        String boatName = boat.getName();
        Boat savedBoat = boatRepository.findByName(boatName).get();
        int boatId = savedBoat.getId();

        addPhotos(boatId, photos);
    }

    public void addPhotos(int boatId, MultipartFile[] photos){
        for(MultipartFile photo : photos){
            addPhoto(boatId, photo, false);
        }
    }

    public void addPhoto(int boatId, MultipartFile photoFile, boolean isDefault){

        if(findById(boatId) == null) {
            throw new EntityNotFoundException("Boat with given id not found.");
        }

        String imageName = fileUploadService.store(photoFile);
        Photo photo = new Photo(imageName, boatId, isDefault);
        photoService.save(photo);
    }

    public void deleteById(int id) {
        boatRepository.deleteById(id);
    }

}
