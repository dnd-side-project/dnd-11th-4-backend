package com.dnd.dndtravel.map.service.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;

import lombok.Builder;

@Builder
public record AttractionRecordResponse(
	long id,
	long entireRecordCount,
	String attractionName,
	String memo,
	LocalDate visitDate,
	String region,
	List<String> photoUrls
) {
	public static AttractionRecordResponse from(RecordProjection recordProjection) {
		return AttractionRecordResponse.builder()
			.id(recordProjection.getMemberAttractionId())
			.entireRecordCount(recordProjection.getEntireRecordCount())
			.attractionName(recordProjection.getAttractionName())
			.memo(recordProjection.getMemo())
			.visitDate(recordProjection.getVisitDate())
			.region(recordProjection.getRegion())
			.photoUrls(recordProjection.getPhotoUrls())
			.build();
	}
}
