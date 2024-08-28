package com.dnd.dndtravel.map.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.MemberAttraction;
import com.dnd.dndtravel.map.repository.custom.MemberAttractionRepositoryCustom;

public interface MemberAttractionRepository extends JpaRepository<MemberAttraction, Long>,
	MemberAttractionRepositoryCustom {
	MemberAttraction findByAttractionIdAndMemberId(Long attractionId, Long memberId);

	List<MemberAttraction> findByMemberId(Long memberId);
}
