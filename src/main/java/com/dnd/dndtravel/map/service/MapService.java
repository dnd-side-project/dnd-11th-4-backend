package com.dnd.dndtravel.map.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.map.domain.Photo;
import com.dnd.dndtravel.map.dto.RecordDto;
import com.dnd.dndtravel.map.domain.Attraction;
import com.dnd.dndtravel.map.domain.MemberAttraction;
import com.dnd.dndtravel.map.domain.MemberRegion;
import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;
import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordResponse;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;
import com.dnd.dndtravel.map.repository.AttractionRepository;
import com.dnd.dndtravel.map.repository.MemberAttractionRepository;
import com.dnd.dndtravel.map.repository.MemberRegionRepository;
import com.dnd.dndtravel.map.repository.PhotoRepository;
import com.dnd.dndtravel.map.repository.RegionRepository;

import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

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
	public RegionResponse allRegions(long memberId) {
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
	public void recordAttraction(RecordDto recordDto, long memberId) {
		// validate
		Region region = regionRepository.findByName(recordDto.region()).orElseThrow(() -> new RuntimeException("존재하지 않는 지역"));
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));

		// 명소 체크후, 새 명소면 DB에 저장
		Attraction attraction = attractionRepository.findByName(recordDto.attractionName())
			.orElseGet(() -> attractionRepository.save(Attraction.of(region, recordDto.attractionName())));

		// 유저가 이미 기록한적 없는 방문명소면 DB에 저장
		MemberAttraction memberAttraction = recordMemberAttraction(recordDto, attraction, member);

		// 사진 업로드
		savePhotos(recordDto.photos(), memberAttraction);

		// 방문횟수 업데이트
		updateRegionVisitCount(member, region);
	}

	// 모든 기록 조회
	@Transactional(readOnly = true)
	public List<AttractionRecordResponse> allRecords(long memberId, long cursorNo, int displayPerPage) {
		//validation
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 유저"));
		List<MemberAttraction> memberAttractions = memberAttractionRepository.findByMemberId(memberId);
		if (memberAttractions.isEmpty()) {
			return List.of();
		}

		// 첫 커서값인 경우
		if (cursorNo <= 0) {
			cursorNo = memberAttractionRepository.maxCursor(member.getId()) - displayPerPage;
		}

		List<RecordProjection> attractionRecords = memberAttractionRepository.findAttractionRecords(memberId, cursorNo, displayPerPage);

		//해당 DTO에 존재하는 memberAttractionId들로 photo url 매핑
		setPhotoToRecords(attractionRecords);

		return attractionRecords.stream()
			.map(AttractionRecordResponse::from)
			.toList();
	}

	private void setPhotoToRecords(List<RecordProjection> attractionRecords) {
		List<AttractionPhotoProjection> attractionPhotos = memberAttractionRepository.findByRecordDtos(
			attractionRecords);

		Map<Long, List<AttractionPhotoProjection>> attractionPhotoIds = attractionPhotos.stream()
			.collect(Collectors.groupingBy(AttractionPhotoProjection::memberAttractionId));

		attractionRecords.forEach(attractionRecord -> attractionRecord.inputPhotoUrls(
			attractionPhotoIds.get(attractionRecord.getMemberAttractionId())));
	}

	private List<RegionDto> updateRegionDto(List<RegionDto> regions, List<MemberRegion> memberRegions) {
		return regions.stream()
			.map(regionDto -> {
				String regionName = regionDto.name();
				Optional<MemberRegion> innerMemberRegion = memberRegions.stream()
					.filter(memberRegion -> memberRegion.isEqualRegion(regionName))
					.findFirst();

				if (innerMemberRegion.isPresent() && innerMemberRegion.get().isVisited()) {
					return new RegionDto(regionDto.name(), innerMemberRegion.get().getVisitCount());
				}
				return regionDto;
			})
			.toList();
	}

	private MemberAttraction recordMemberAttraction(RecordDto recordDto, Attraction attraction, Member member) {
		MemberAttraction memberAttraction = memberAttractionRepository.findByAttractionIdAndMemberId(
			attraction.getId(), member.getId());

		if (memberAttraction == null) {
			memberAttraction = MemberAttraction.of(member, attraction, recordDto.memo(),
				recordDto.dateTime(), recordDto.region(), recordDto.attractionName());
			memberAttractionRepository.save(memberAttraction);
		}
		return memberAttraction;
	}

	private void updateRegionVisitCount(Member member, Region region) {
		MemberRegion memberRegion = memberRegionRepository.findByMemberIdAndRegionId(member.getId(),
			region.getId());
		if (memberRegion == null) {
			memberRegionRepository.save(MemberRegion.of(member, region));
		} else {
			memberRegion.addVisitCount();
		}
	}

	private void savePhotos(List<MultipartFile> photos, MemberAttraction memberAttractionEntity) {
		for (MultipartFile photo : photos) {
			String imageUrl = photoService.upload(photo);
			photoRepository.save(Photo.of(memberAttractionEntity, imageUrl));
		}
	}
}
