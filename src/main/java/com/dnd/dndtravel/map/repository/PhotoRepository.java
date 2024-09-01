package com.dnd.dndtravel.map.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.Photo;
import com.dnd.dndtravel.map.repository.custom.PhotoRepositoryCustom;

public interface PhotoRepository extends JpaRepository<Photo, Long>, PhotoRepositoryCustom {

	List<Photo> findByMemberAttractionId(Long id);
}
