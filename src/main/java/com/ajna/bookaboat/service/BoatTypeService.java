package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.respository.BoatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatTypeService {

    @Autowired
    BoatTypeRepository boatTypeRepository;

    public List<BoatType> findAll(){
        return boatTypeRepository.findAll();
    }

    public BoatType findById(int id){
        BoatType boatType = null;
        Optional<BoatType> optBoatType = boatTypeRepository.findById(id);
        if(optBoatType.isPresent()){
            boatType = optBoatType.get();
        }
        return boatType;
    }

    public BoatType findByName(String name){
        BoatType boatType = null;
        Optional<BoatType> optBoatType = boatTypeRepository.findByName(name);
        if(optBoatType.isPresent()){
            boatType = optBoatType.get();
        }
        return boatType;
    }

    public void save(BoatType boatType){
        boatTypeRepository.save(boatType);
    }

    public void deleteById(int id){
        boatTypeRepository.deleteById(id);
    }
}
