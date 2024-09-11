package com.dnd.dndtravel.map.service.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDate;
import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AttractionRecordResponse(
	@Schema(description = "방문기록 id", requiredMode = REQUIRED)
	long id,
	@Schema(description = "지역명", requiredMode = REQUIRED)
	String region,
	@Schema(description = "명소명", requiredMode = REQUIRED)
	String attractionName,
	@Schema(description = "메모", requiredMode = NOT_REQUIRED)
	String memo,
	@Schema(description = "방문날짜, ISO Date(yyyy-MM-dd)", requiredMode = NOT_REQUIRED)
	LocalDate visitDate,
	@Schema(description = "이미지 URL들", requiredMode = NOT_REQUIRED)
	List<String> photoUrls
) {
	public static AttractionRecordResponse from(RecordProjection recordProjection) {
		return AttractionRecordResponse.builder()
			.id(recordProjection.getMemberAttractionId())
			.attractionName(recordProjection.getAttractionName())
			.memo(recordProjection.getMemo())
			.visitDate(recordProjection.getVisitDate())
			.region(recordProjection.getRegion())
			.photoUrls(recordProjection.getPhotoUrls())
			.build();
	}
}
