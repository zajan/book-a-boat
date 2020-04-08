package com.ajna.bookaboat;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.BoatType;
import com.ajna.bookaboat.entity.Role;
import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.service.BoatService;
import com.ajna.bookaboat.service.BoatTypeService;
import com.ajna.bookaboat.service.RoleService;
import com.ajna.bookaboat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
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

    public static void main(String[] args) {
        SpringApplication.run(BookABoatApplication.class, args);
    }


    @PostConstruct
    public void init(){
        addSampleData();
    }

    public void addSampleData() {
        Role roleAdmin = addSampleRole("ROLE_ADMIN");
        Role roleUser = addSampleRole("ROLE_USER");

        User user = addSampleUser("user", "user", new ArrayList<>(Arrays.asList(roleUser)));
        User admin = addSampleUser("admin", "admin", new ArrayList<>(Arrays.asList(roleUser, roleAdmin)));

        BoatType yacht = addSampleBoatType("Yacht");
        BoatType motorboat = addSampleBoatType("Motorboat");

        Boat mint = addSampleBoat("Mint", yacht);
        Boat chilli = addSampleBoat("Chilli", motorboat);
    }

    private Role addSampleRole(String roleName) {

        Role role = roleService.findByName(roleName);

        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleService.save(role);

            role = roleService.findByName(roleName);
        }
        return role;
    }

    private User addSampleUser(String username, String password, ArrayList<Role> roles) {
        User user = userService.findByUsername(username);

        if (user == null) {
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
        BoatType boatType = boatTypeService.findByName(boatTypeName);
        if (boatType == null) {
            boatType = new BoatType();
            boatType.setName(boatTypeName);
            boatTypeService.save(boatType);
            boatType = boatTypeService.findByName(boatTypeName);
        }
        return boatType;
    }

    private Boat addSampleBoat(String name, BoatType boatType){
        Boat boat = boatService.findByName(name);
        if(boat == null){
            boat = new Boat();
            boat.setName(name);
            boat.setBoatType(boatType);
            boatService.save(boat);
            boat = boatService.findByName(name);
        }
        return boat;

    }

}
