package com.ajna.bookaboat.service;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.Photo;
import com.ajna.bookaboat.respository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    public Iterable<Boat> findAll(BoatFilter boatFilter, Pageable pageable){
        return boatRepository.findAll(boatFilter, pageable);
    }


    public Boat findById(int id) {
        Optional<Boat> optBoat = boatRepository.findById(id);

        if (!optBoat.isPresent()) {
            throw new EntityNotFoundException("Couldn't find boat with id: " + id);
        }
        return optBoat.get();
    }

    public Boat findByName(String name) {
        Optional<Boat> optBoat = boatRepository.findByName(name);

        if (!optBoat.isPresent()) {
            throw new EntityNotFoundException("Couldn't find boat with name: " + name);
        }
        return optBoat.get();
    }

    public ResponseEntity<Resource> getDefaultPhotoFile(int id, HttpServletRequest request) {
        Photo photo = photoService.findDefaultForBoat(id);

        return photoService.getPhotoFileByName(photo.getName(), request);
    }

    public Boat save(Boat boat) {

        return boatRepository.save(boat);
    }

    public Boat save(Boat boat, MultipartFile[] photos) {

        Boat savedBoat =  boatRepository.save(boat);
        addPhotos(savedBoat.getId(), photos);

        return savedBoat;
    }

    public List<Photo> addPhotos(int boatId, MultipartFile[] photos) {
        List<Photo> savedPhotos = new ArrayList<>();
        for (MultipartFile photo : photos) {
            savedPhotos.add(addPhoto(boatId, photo, false));
        }
        return savedPhotos.isEmpty() ? null : savedPhotos;
    }

    public Photo addPhoto(int boatId, MultipartFile photoFile, boolean isDefault) {

        if (findById(boatId) == null) {
            throw new EntityNotFoundException("Boat with given id not found.");
        }

        String imageName = fileUploadService.store(photoFile);
        Photo photo = new Photo(imageName, boatId, isDefault);
        return photoService.save(photo);
    }

    public void setPhotoAsDefault(int boatId, String photoName) {
        Photo oldDefaultPhoto = photoService.findDefaultForBoat(boatId);
        if (oldDefaultPhoto != null) {
            oldDefaultPhoto.setDefault(false);
        }

        Photo photo = photoService.findByName(photoName);
        if (photo == null) {
            throw new EntityNotFoundException("Couldn't find photo with name: " + photoName);
        }
        photo.setDefault(true);
        photoService.save(photo);
    }

    public void deleteById(int id) {
        boatRepository.deleteById(id);
    }

}
