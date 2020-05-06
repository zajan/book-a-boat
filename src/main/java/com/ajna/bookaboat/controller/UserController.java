package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.entity.Role;
import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.service.BookingService;
import com.ajna.bookaboat.service.RoleService;
import com.ajna.bookaboat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user){
        Role userRole = roleService.findByName("ROLE_USER");
        user.setRoles(new ArrayList<>(Arrays.asList(userRole)));
        return userService.save(user);
    }

    @PostMapping("/set-admin/{user_id}")
    public User setAdmin(@PathVariable int user_id){
        User user = userService.findById(user_id);
        Role adminRole = roleService.findByName("ROLE_ADMIN");
        user.getRoles().add(adminRole);
        return userService.save(user);
    }

    @GetMapping("/me/bookings")
    public List<Booking> getMyBookings(Authentication auth){
        return bookingService.findForUser(auth.getPrincipal().toString());
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
