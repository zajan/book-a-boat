package com.ajna.bookaboat.controller;


import com.ajna.bookaboat.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/photos")
public class PhotosController {

    @Autowired
    PhotoService photoService;

    @DeleteMapping("/{name}")
    public void deletePhoto(@PathVariable String name){
        photoService.deleteByName(name);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String name, HttpServletRequest request){
        return photoService.getPhotoFileByName(name, request);
    }
}
