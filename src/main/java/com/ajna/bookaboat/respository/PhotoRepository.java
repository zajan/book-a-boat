package com.ajna.bookaboat.respository;

import com.ajna.bookaboat.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    int deleteByName(String name);
}
