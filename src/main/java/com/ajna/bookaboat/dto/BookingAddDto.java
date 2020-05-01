package com.ajna.bookaboat.dto;

import com.ajna.bookaboat.entity.Boat;
import com.ajna.bookaboat.entity.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingAddDto {

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateStart;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateEnd;

    @NotNull
    private int boatId;

    public BookingAddDto() {
    }

    public BookingAddDto(LocalDate startDate, LocalDate endDate, int boatId) {
        this.dateStart = startDate;
        this.dateEnd = endDate;
        this.boatId = boatId;
    }

    public LocalDate getStartDate() {
        return dateStart;
    }

    public void setStartDate(LocalDate startDate) {
        this.dateStart = startDate;
    }

    public LocalDate getEndDate() {
        return dateEnd;
    }

    public void setEndDate(LocalDate endDate) {
        this.dateEnd = endDate;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    public Booking convertToBooking(){
        Boat boat = new Boat();
        boat.setId(boatId);
        return new Booking(0, null, boat, dateStart, dateEnd);
    }

    @Override
    public String toString() {
        return "\nBookingAddDto{" +
                "startDate=" + dateStart +
                ", endDate=" + dateEnd +
                ", boatId=" + boatId +
                '}';
    }
}
