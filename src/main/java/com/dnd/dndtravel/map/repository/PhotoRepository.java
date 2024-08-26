package com.dnd.dndtravel.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
