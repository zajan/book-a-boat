package com.ajna.bookaboat.controller;

import com.ajna.bookaboat.entity.Booking;
import com.ajna.bookaboat.exception.BookingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFound(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BookingException.class)
    public void handleBookingException(HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
