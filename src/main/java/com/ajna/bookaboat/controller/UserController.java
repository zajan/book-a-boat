package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.entity.Role;
import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("sign-up")
    public User signUp(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/me")
    public User getMe(Authentication auth){
        return userService.findByUsername(auth.getPrincipal().toString());
    }


}
