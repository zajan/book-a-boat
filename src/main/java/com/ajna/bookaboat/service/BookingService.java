package com.ajna.bookaboat.service;


import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.entity.User;
import com.ajna.bookaboat.exception.BookingException;
import com.ajna.bookaboat.respository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> findAll(Specification<Booking> specification) {
        return bookingRepository.findAll(specification);
    }

    public List<Booking> findForUser(String username){
        return bookingRepository.findByUser_Username(username);
    }

    public List<Booking> findForBoat(int boatId){
        return bookingRepository.findByBoat_Id(boatId);
    }

    public void deletaAll() {
        bookingRepository.deleteAll();
    }

    public Booking findById(int id) {
        Optional<Booking> optBooking = bookingRepository.findById(id);
        if (!optBooking.isPresent()) {
            throw new EntityNotFoundException("Couldn't find booking with id: " + id);
        }
        return optBooking.get();
    }

    public Booking save(Booking booking) {

        if (!BookingValidator.isCorrect(booking)) {
            throw new BookingException("Booking end date is before start date.");
        }

        if (!BookingValidator.isInFuture(booking)) {
            throw new BookingException("The booking start date must be in future. Start date: " + booking.getDateStart());
        }

        Specification<Booking> spec = BookingValidator.bookingsAtThisTime(booking);

        List<Booking> bookingsAtThisTime = findAll(spec);
        if (!bookingsAtThisTime.isEmpty()) {
            throw new BookingException("Boat with id = " + booking.getBoat().getId() + " is not available at this time: "
                    + booking.getDateStart() + " - " + booking.getDateEnd());
        }

        return bookingRepository.save(booking);
    }

    public void deleteById(int id) {
        bookingRepository.deleteById(id);
    }

}
