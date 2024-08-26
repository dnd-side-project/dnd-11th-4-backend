package com.dnd.dndtravel.map.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.MemberAttraction;

public interface MemberAttractionRepository extends JpaRepository<MemberAttraction, Long> {
	MemberAttraction findByAttractionIdAndMemberId(Long attractionId, Long memberId);
}
