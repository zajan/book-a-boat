package com.ajna.bookaboat.respository;

import com.ajna.bookaboat.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer>, JpaSpecificationExecutor<Booking> {

    List<Booking> findByUser_Username(String username);
}
