package com.dnd.dndtravel.map.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

	Optional<Attraction> findByName(String name);
}
