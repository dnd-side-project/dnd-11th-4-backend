package com.dnd.dndtravel.map.repository.dto.projection;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import com.dnd.dndtravel.map.domain.Attraction;

import lombok.Getter;

/**
 * record로 설계하려 했으나, photoUrls를 뒤늦게 주입시켜줘야하는 상황이므로 class로 설계
 */
@Getter
public class RecordProjection {
	private final long memberAttractionId;
	private final String memo;
	private final LocalDate visitDate;
	private final String region;
	private final Attraction attraction;
	private List<String> photoUrls;

	public RecordProjection(long memberAttractionId, String memo,
		LocalDate visitDate, String region, Attraction attraction) {
		this.memberAttractionId = memberAttractionId;
		this.memo = memo;
		this.visitDate = visitDate;
		this.region = region;
		this.attraction = attraction;
	}

	public void inputPhotoUrls(List<AttractionPhotoProjection> attractionPhotoProjections) {
		if (attractionPhotoProjections != null) {
			this.photoUrls = attractionPhotoProjections.stream()
				.map(AttractionPhotoProjection::photoUrl)
				.filter(Objects::nonNull)
				.toList();
		}
	}

	public String getAttractionName() {
		return this.attraction.getName();
	}
}
