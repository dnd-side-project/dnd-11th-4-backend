package com.dnd.dndtravel.map.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.repository.MapRepository;
import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MapService {
	private final RegionRepository regionRepository;
	private final MemberRepository memberRepository;
	private final MemberRegionRepository memberRegionRepository;

	// 모든 지역 조회(홈)
	@Transactional(readOnly = true)
	public RegionResponse allRegions(Long memberId) {
		//todo custom ex
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));

		// 유저의 지역 방문기록 전부 가져온다
		List<MemberRegion> memberRegions = memberRegionRepository.findByMemberId(memberId);
		List<RegionDto> regions = regionRepository.findAll().stream()
			.map(RegionDto::from)
			.toList();

		if (memberRegions.isEmpty()) {
			return new RegionResponse(regions, member.getSelectedColor());
		}

		return new RegionResponse(
			updateRegionDto(regions, memberRegions),
			(int)memberRegions.stream()
				.filter(MemberRegion::isVisited)
				.count(),
			member.getSelectedColor()
		);
	}
	}
}
