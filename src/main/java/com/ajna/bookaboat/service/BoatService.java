package com.ajna.bookaboat.service;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.respository.BoatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class BoatService {

    @Autowired
    BoatRepository boatRepository;


    public List<Boat> findAll(){
        return boatRepository.findAll();
    }

    public Boat findById(int id){
        Boat boat = null;
        Optional<Boat> optBoat = boatRepository.findById(id);
        if(optBoat.isPresent()){
            boat = optBoat.get();
        }
        return boat;
    }

    public Boat findByName(String name){
        Boat boat = null;
        Optional<Boat> optBoat = boatRepository.findByName(name);
        if(optBoat.isPresent()){
            boat = optBoat.get();
        }
        return boat;
    }

    public void save(Boat boat){
        boatRepository.save(boat);
    }

    public void deleteById(int id){
        boatRepository.deleteById(id);
    }

}
