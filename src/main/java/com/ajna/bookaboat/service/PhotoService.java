package com.ajna.bookaboat.service;

import com.ajna.bookaboat.entity.Photo;
import com.ajna.bookaboat.respository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhotoService {
    
    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    FileUploadService fileUploadService;

    public List<Photo> findAll(){
        return photoRepository.findAll();
    }

    public Photo findById(int id){
        Photo photo = null;
        Optional<Photo> optPhoto = photoRepository.findById(id);
        if(optPhoto.isPresent()){
            photo = optPhoto.get();
        }
        return photo;
    }

    public Photo findByName(String name){
        Photo photo = null;
        Optional<Photo> optPhoto = photoRepository.findByName(name);
        if(optPhoto.isPresent()){
            photo = optPhoto.get();
        }
        return photo;
    }

    public Photo findDefaultForBoat(int boatId){
        Photo photo = null;
        Optional<Photo> optPhoto = photoRepository.findByBoatIdAndIsDefault(boatId, true);
        if(optPhoto.isPresent()){
            photo = optPhoto.get();
        }
        return photo;
    }


    public void save(Photo photo){
        photoRepository.save(photo);
    }

    public void deleteById(int id){
        photoRepository.deleteById(id);
    }
    public void deleteByName(String name){
        photoRepository.deleteByName(name);
        fileUploadService.delete(name);
    }

    public ResponseEntity<Resource> getPhotoFileByName(String name, HttpServletRequest request){
        Resource resource = fileUploadService.loadAsResource(name);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
