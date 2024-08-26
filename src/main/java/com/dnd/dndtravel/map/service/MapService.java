package com.dnd.dndtravel.map.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.map.domain.Photo;
import com.dnd.dndtravel.map.dto.RecordDto;
import com.dnd.dndtravel.map.domain.Attraction;
import com.dnd.dndtravel.map.domain.MemberAttraction;
import com.dnd.dndtravel.map.domain.MemberRegion;
import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;
import com.dnd.dndtravel.map.repository.AttractionRepository;
import com.dnd.dndtravel.map.repository.MemberAttractionRepository;
import com.dnd.dndtravel.map.repository.MemberRegionRepository;
import com.dnd.dndtravel.map.repository.PhotoRepository;
import com.dnd.dndtravel.map.repository.RegionRepository;

import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;

import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MapService {
	private final PhotoService photoService;
	private final RegionRepository regionRepository;
	private final MemberRepository memberRepository;
	private final AttractionRepository attractionRepository;
	private final MemberAttractionRepository memberAttractionRepository;
	private final MemberRegionRepository memberRegionRepository;
	private final PhotoRepository photoRepository;

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

	@Transactional
	public void recordAttraction(RecordDto recordDto, Long memberId) {
		// Attraction(명소)가 이미 있으면 save하지않음
		Attraction attraction = attractionRepository.findByName(recordDto.attractionName());
		Region region = regionRepository.findByName(recordDto.region()).orElseThrow(() -> new RuntimeException("잘못된 지역이름"));

		// 명소가 없으면 입력받은 지역과 명소를 통해 새로운 명소를 등록
		if (attraction == null) {
			attractionRepository.save(Attraction.of(region, recordDto.attractionName()));
		}

		// 방문 기록을 등록
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));
		MemberAttraction memberAttraction = MemberAttraction.of(member, attraction, recordDto.memo(),
			recordDto.dateTime(), region.getName());
		memberAttractionRepository.save(memberAttraction);

		// 사진을 저장
		for (MultipartFile photo : recordDto.photos()) {
			String imageUrl = photoService.upload(photo);
			photoRepository.save(Photo.of(memberAttraction, imageUrl));
		}
	}
	private List<RegionDto> updateRegionDto(List<RegionDto> regions, List<MemberRegion> memberRegions) {
		return regions.stream()
			.map(regionDto -> {
				String regionName = regionDto.name();
				Optional<MemberRegion> innerMemberRegion = memberRegions.stream()
					.filter(memberRegion -> memberRegion.getRegion().isEqualTo(regionName))
					.findFirst();

				if (innerMemberRegion.isPresent() && innerMemberRegion.get().isVisited()) {
					return new RegionDto(regionDto.name(), innerMemberRegion.get().getVisitCount());
				}
				return regionDto;
			})
			.toList();
	}
}
