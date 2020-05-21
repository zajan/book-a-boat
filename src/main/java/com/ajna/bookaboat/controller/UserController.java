package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.dto.UserAddDto;
import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.entity.Role;
import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.service.BookingService;
import com.ajna.bookaboat.service.RoleService;
import com.ajna.bookaboat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
    public User signUp(@Valid @RequestBody UserAddDto userDto){
        User user = userDto.convertToUser();
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

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userService.deleteById(id);
    }

    @PutMapping("/me")
    public User updateMe(@RequestBody UserAddDto userDto, Authentication auth){
        User user = userService.findByUsername(auth.getPrincipal().toString());
        User newUser = userDto.convertToUser(user);

        return userService.save(newUser);
    }

    @GetMapping("/me")
    public User getMe(Authentication auth){
        return userService.findByUsername(auth.getPrincipal().toString());
    }


}
