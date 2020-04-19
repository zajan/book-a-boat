package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.respository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;


    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(int id) {
        Optional<Booking> optBooking = bookingRepository.findById(id);
        if (!optBooking.isPresent()) {
            throw new EntityNotFoundException("Couldn't find booking with id: " + id);
        }
        return optBooking.get();
    }

    public void save(Booking booking) {
        // TODO: check if available
        bookingRepository.save(booking);
    }

    public void deleteById(int id) {
        bookingRepository.deleteById(id);
    }

}
