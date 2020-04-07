package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.Role;
import com.ajna.bookaboat.respository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Role role = null;
        Optional<Role> optRole = roleRepository.findById(id);
        if(optRole.isPresent()){
            role = optRole.get();
        }
        return role;
    }

    public Role findByName(String name){
        Role role = null;
        Optional<Role> optRole = roleRepository.findByName(name);
        if(optRole.isPresent()){
            role = optRole.get();
        }
        return role;
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    public void deleteById(int id){
        roleRepository.deleteById(id);
    }
}
