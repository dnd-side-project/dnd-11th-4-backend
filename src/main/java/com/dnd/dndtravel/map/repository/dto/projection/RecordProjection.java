package com.dnd.dndtravel.map.repository.dto.projection;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * record로 설계하려 했으나, photoUrls를 뒤늦게 주입시켜줘야하는 상황이므로 class로 설계
 */
@Getter
public class RecordProjection {
	private final long memberAttractionId;
	private final long entireRecordCount;
	private final String attractionName;
	private final String memo;
	private final LocalDate visitDate;
	private final String region;
	private List<String> photoUrls;

	public RecordProjection(long memberAttractionId, long entireRecordCount, String attractionName, String memo,
		LocalDate visitDate, String region) {
		this.memberAttractionId = memberAttractionId;
		this.entireRecordCount = entireRecordCount;
		this.attractionName = attractionName;
		this.memo = memo;
		this.visitDate = visitDate;
		this.region = region;
	}

	public void inputPhotoUrls(List<AttractionPhotoProjection> attractionPhotoProjections) {
		if (attractionPhotoProjections != null) {
			this.photoUrls = attractionPhotoProjections.stream()
				.map(AttractionPhotoProjection::photoUrl)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		}
	}
}
