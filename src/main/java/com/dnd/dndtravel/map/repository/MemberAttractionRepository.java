package com.dnd.dndtravel.map.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.MemberAttraction;
import com.dnd.dndtravel.map.repository.custom.MemberAttractionRepositoryCustom;

public interface MemberAttractionRepository extends JpaRepository<MemberAttraction, Long>,
	MemberAttractionRepositoryCustom {

	List<MemberAttraction> findByMemberId(long memberId);

	Optional<MemberAttraction> findByIdAndMemberId(long memberAttractionId, long memberId);
}

