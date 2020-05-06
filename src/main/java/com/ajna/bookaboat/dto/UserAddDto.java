package com.ajna.bookaboat.dto;

import com.ajna.bookaboat.entity.User;

import javax.validation.constraints.NotEmpty;

public class UserAddDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String email;

    private String firstName;

    private String lastName;

    public UserAddDto() {
    }

    public UserAddDto(@NotEmpty String username, @NotEmpty String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User convertToUser(){
        return new User(0, username, password, email, firstName, lastName);
    }

    public User convertToUser(User user){
        if(!username.isEmpty()){
            user.setUsername(username);
        }
        if (!firstName.isEmpty()){
            user.setFirstName(firstName);
        }
        if(!lastName.isEmpty()){
            user.setLastName(lastName);
        }
        if (!email.isEmpty()){
            user.setEmail(email);
        }

        if(!password.isEmpty()){
            user.setPassword(password);
        }
        return user;
    }
}
