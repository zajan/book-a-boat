package com.ajna.bookaboat.respository;

import com.ajna.bookaboat.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
