package com.dnd.dndtravel.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.Region;

public interface MapRepository extends JpaRepository<Region, Long> {
}
