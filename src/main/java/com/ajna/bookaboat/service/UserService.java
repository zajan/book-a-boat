package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        Optional<User> optUser = userRepository.findById(id);

        if(!optUser.isPresent()){
            throw new EntityNotFoundException("Couldn't find user with id: " + id);
        }

        return optUser.get();
    }
    public User findByUsername(String username){
        Optional<User> optUser = userRepository.findByUsername(username);

        if(!optUser.isPresent()){
            throw new EntityNotFoundException("Couldn't find user with username: " + username);
        }

        return optUser.get();
    }


    public void save(User user){
        userRepository.save(user);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }


}
