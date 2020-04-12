package com.ajna.bookaboat.respository;

import com.ajna.bookaboat.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    Optional<Photo> findByName(@Param("name")String name);
    int deleteByName(@Param("name")String name);
    Optional<Photo> findByBoatIdAndIsDefault(@Param("boat_id")int boatId, @Param("is_default") boolean isDefault);

}
