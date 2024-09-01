package com.dnd.dndtravel.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

}
