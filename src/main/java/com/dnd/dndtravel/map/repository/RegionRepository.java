package com.dnd.dndtravel.map.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	Optional<Region> findByName(String region);
}
