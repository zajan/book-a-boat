package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.Role;
import com.ajna.bookaboat.respository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findById(int id){
        Optional<Role> optRole = roleRepository.findById(id);
        if(!optRole.isPresent()){
            throw new EntityNotFoundException("Couldn't find role with id: " + id);
        }
        return optRole.get();
    }

    public Role findByName(String name){
        Optional<Role> optRole = roleRepository.findByName(name);
        if(!optRole.isPresent()){
            throw new EntityNotFoundException("Couldn't find role with name: " + name);
        }
        return optRole.get();
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    public void deleteById(int id){
        roleRepository.deleteById(id);
    }
}
