package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.respository.BoatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        Optional<BoatType> optBoatType = boatTypeRepository.findById(id);
        if(!optBoatType.isPresent()){
            throw new EntityNotFoundException("Couldn't find booking with id: " + id);
        }
        return optBoatType.get();
    }

    public BoatType findByName(String name){
        Optional<BoatType> optBoatType = boatTypeRepository.findByName(name);
        if(!optBoatType.isPresent()){
            throw new EntityNotFoundException("Couldn't find booking with name: " + name);
        }
        return optBoatType.get();
    }

    public BoatType save(BoatType boatType){
        return boatTypeRepository.save(boatType);
    }

    public void deleteById(int id){
        boatTypeRepository.deleteById(id);
    }
}
