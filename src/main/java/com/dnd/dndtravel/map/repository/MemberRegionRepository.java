package com.dnd.dndtravel.map.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnd.dndtravel.map.domain.MemberRegion;

public interface MemberRegionRepository extends JpaRepository<MemberRegion, Long> {
	List<MemberRegion> findByMemberId(Long memberId);

	MemberRegion findByMemberIdAndRegionId(Long memberId, Long regionId);
}
