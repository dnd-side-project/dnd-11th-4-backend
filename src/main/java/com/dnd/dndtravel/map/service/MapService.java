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
import com.dnd.dndtravel.map.exception.MemberAttractionNotFoundException;
import com.dnd.dndtravel.map.exception.MemberNotFoundException;
import com.dnd.dndtravel.map.exception.RegionNotFoundException;
import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;
import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordResponse;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordsResponse;
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
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

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
		Region region = regionRepository.findByName(recordDto.region()).orElseThrow(() -> new RegionNotFoundException(recordDto.region()));
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));

		Attraction attraction = attractionRepository.save(Attraction.of(region, recordDto.attractionName()));

		MemberAttraction memberAttraction = memberAttractionRepository.save(
			MemberAttraction.of(member, attraction, recordDto.memo(),
				recordDto.dateTime(), recordDto.region()));

		// 사진 업로드
		savePhotos(recordDto.photos(), memberAttraction);

		// 방문횟수 업데이트
		updateRegionVisitCount(member, region);
	}

	// 모든 기록 조회
	@Transactional(readOnly = true)
	public AttractionRecordsResponse allRecords(long memberId, long cursorNo, int displayPerPage) {
		//전체 방문 기록 수 조회
		Long visitCount = memberAttractionRepository.entireVisitCount(memberId);

		//방문기록이 없는경우
		if (visitCount == 0) {
			return new AttractionRecordsResponse(0, List.of());
		}

		List<RecordProjection> attractionRecords = memberAttractionRepository.findAttractionRecords(memberId, cursorNo, displayPerPage);

		//사진 URL 저장
		setPhotoUrlsWithJoin(attractionRecords);

		return new AttractionRecordsResponse(visitCount, attractionRecords.stream()
			.map(AttractionRecordResponse::from)
			.toList());
	}

	// 방문기록 수정
	//todo DB 컬럼을 매번 지웠다가 insert하는게 조금 부자연스럽다고 느낌, 추후 개선포인트 고려해보자
	@Transactional
	public void updateVisitRecord(RecordDto dto, long memberId, long memberAttractionId) {
		//validation
		MemberAttraction memberAttraction = memberAttractionRepository.findByIdAndMemberId(memberAttractionId, memberId).orElseThrow(() -> new MemberAttractionNotFoundException(memberId, memberAttractionId));

		//update
		memberAttraction.updateVisitRecord(dto.region(), dto.dateTime(), dto.memo());
		Attraction attraction = attractionRepository.findById(memberAttraction.getAttraction().getId()).orElseThrow(() -> new RuntimeException("유효하지 않은 명소이름"));
		attraction.updateAttractionName(dto.attractionName());

		//사진 업데이트
		updatePhotos(memberAttraction, dto.photos());
	}

	/**
	 * 기록에 저장된 사진이 없는경우, 수정요청엔 사진이 있는경우 => s3에 업로드, DB에 새로 사진추가
	 * 기록에 저장된 사진이 없는경우, 수정요청엔 사진이 없는경우 => 아무작업도 안해도 됨
	 * 기록에 저장된 사진이 있는경우, 수정요청엔 사진이 있는경우 => s3에 있던 사진 전부 삭제후 재 업로드, DB에 있던 사진 전부 삭제 후 새로운 사진 추가
	 * 기록에 저장된 사진이 있는경우, 수정요청엔 사진이 없는경우 => s3에 있던 사진 전부삭제, DB에 있던 사진 전부 삭제
	 */
	private void updatePhotos(MemberAttraction memberAttraction, List<MultipartFile> newPhotos) {
		// 방문기록에 저장된 사진 조회
		List<Photo> existingPhotos = photoRepository.findByMemberAttractionId(memberAttraction.getId());
		List<String> existingUrls = existingPhotos.stream()
			.map(Photo::getUrl)
			.toList();

		// 방문기록에 저장된 사진이 없고, 수정요청에도 사진이 없는경우
		if (existingPhotos.isEmpty() && (newPhotos == null || newPhotos.isEmpty())) {
			return;
		}

		// 기존의 사진이 존재하는 경우
		if (!existingPhotos.isEmpty()) {
			photoService.deleteS3Photo(existingUrls);
			photoRepository.deleteAll(existingPhotos);
		}

		// 새로운 사진 수정요청이 들어오는경우, S3에 업로드 후 DB에 추가
		if (newPhotos != null && !newPhotos.isEmpty()) {
			List<String> newPhotoUrls = newPhotos.stream()
				.map(photoService::upload)
				.toList();

			List<Photo> newPhotoEntities = newPhotoUrls.stream()
				.map(url -> Photo.of(memberAttraction, url))
				.toList();
			photoRepository.saveAll(newPhotoEntities);
		}
	}

	// 방문기록 삭제
	@Transactional
	public void deleteRecord(long memberId, long memberAttractionId) {
		//validation
		MemberAttraction memberAttraction = memberAttractionRepository.findByIdAndMemberId(memberAttractionId, memberId)
			.orElseThrow(() -> new MemberAttractionNotFoundException(memberAttractionId, memberId));

		List<Photo> photos = photoRepository.findByMemberAttractionId(memberAttraction.getId());
		photoRepository.deleteAll(photos);
		memberAttractionRepository.delete(memberAttraction);
		memberRegionRepository.deleteById(memberAttraction.getMember().getId());
		attractionRepository.deleteById(memberAttraction.getAttraction().getId());
	}

	private void setPhotoUrlsWithJoin(List<RecordProjection> attractionRecords) {
		List<AttractionPhotoProjection> attractionPhotos = photoRepository.findByRecordDtos(
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
		if (photos != null && !photos.isEmpty()) {
			for (MultipartFile photo : photos) {
				String imageUrl = photoService.upload(photo);
				photoRepository.save(Photo.of(memberAttractionEntity, imageUrl));
			}
		}
	}
}
