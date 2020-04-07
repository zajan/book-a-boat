package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(int id){
        User user = null;
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            user = optUser.get();
        }
        return user;
    }
    public User findByUsername(String username){
        User user = null;
        Optional<User> optUser = userRepository.findByUsername(username);
        if(optUser.isPresent()){
            user = optUser.get();
        }
        return user;
    }


    public void save(User user){
        userRepository.save(user);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }


}
