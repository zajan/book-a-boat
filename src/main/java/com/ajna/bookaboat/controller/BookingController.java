package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.dto.BookingAddDto;
import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.service.BookingService;
import com.ajna.bookaboat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public List<Booking> getAll() {
        return bookingService.findAll(null);
    }


    @PostMapping("")
    public Booking save(@RequestBody @Valid BookingAddDto bookingDto, Authentication auth) {
        User user = userService.findByUsername(auth.getPrincipal().toString());
        Booking booking = bookingDto.convertToBooking();
        booking.setUser(user);
        return bookingService.save(booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingById(@PathVariable int id){
        bookingService.deleteById(id);
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        bookingService.deletaAll();
    }
}
