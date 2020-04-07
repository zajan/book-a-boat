package com.ajna.bookaboat.respository;

import com.ajna.bookaboat.entity.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoatTypeRepository extends JpaRepository<BoatType, Integer> {
    Optional<BoatType> findByName(@Param("name") String name);

}
