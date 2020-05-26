package com.ajna.bookaboat;

import com.ajna.bookaboat.entity.*;
import com.ajna.bookaboat.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties
public class BookABoatApplication {

    @Autowired
    private RoleService roleService;

    @Autowired
    private BoatTypeService boatTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoatService boatService;

    @Autowired
    BookingService bookingService;

    public static void main(String[] args) {
        SpringApplication.run(BookABoatApplication.class, args);
    }


    @PostConstruct
    public void init(){
        addSampleData();
    }
//TODO: this methods should not be in the main application class
    public void addSampleData() {
        Role roleAdmin = addSampleRole("ROLE_ADMIN");
        Role roleUser = addSampleRole("ROLE_USER");

        User user = addSampleUser("user", "user", new ArrayList<>(Arrays.asList(roleUser)));
        User admin = addSampleUser("admin", "admin", new ArrayList<>(Arrays.asList(roleUser, roleAdmin)));

        BoatType yacht = addSampleBoatType("Yacht");
        BoatType motorboat = addSampleBoatType("Motorboat");

        Boat mint = addSampleBoat("Mint", yacht);
        Boat chilli = addSampleBoat("Chilli", motorboat);

        Booking booking = addSampleBooking(user, mint);
    }

    private Role addSampleRole(String roleName) {

        Role role;
        try{
            role = roleService.findByName(roleName);
        } catch (EntityNotFoundException e){
            role = new Role();
            role.setName(roleName);
            roleService.save(role);
            role = roleService.findByName(roleName);
        }


        return role;
    }

    private User addSampleUser(String username, String password, ArrayList<Role> roles) {
        User user;

        try {
            user = userService.findByUsername(username);
        } catch (EntityNotFoundException e){
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRoles(roles);
            userService.save(user);
            user = userService.findByUsername(username);
        }

        return user;
    }

    private BoatType addSampleBoatType(String boatTypeName) {
        BoatType boatType;
        try{
            boatType = boatTypeService.findByName(boatTypeName);
        } catch (EntityNotFoundException e){
            boatType = new BoatType();
            boatType.setName(boatTypeName);
            boatTypeService.save(boatType);
            boatType = boatTypeService.findByName(boatTypeName);
        }
        return boatType;
    }

    private Boat addSampleBoat(String name, BoatType boatType){
        Boat boat;
        try{
            boat = boatService.findByName(name);
        } catch (EntityNotFoundException e){
            boat = new Boat();
            boat.setName(name);
            boat.setBoatType(boatType);
            boatService.save(boat);
            boat = boatService.findByName(name);
        }

        return boat;
    }

    private Booking addSampleBooking(User user, Boat boat){
        Booking booking =  new Booking();
        booking.setUser(user);
        booking.setBoat(boat);
        // TODO: currently this brakes application as on every restart we try to rebook the same boat
        //  which is forbidden and cause exception
        // so we should verify if boat is avieable yet
        booking.setDateStart(LocalDate.of(2021, 05, 1));
        booking.setDateEnd(LocalDate.of(2021, 05, 6));
        return bookingService.save(booking);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
