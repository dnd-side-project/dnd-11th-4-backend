package com.dnd.dndtravel.map.service.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record AttractionRecordsResponse(
	@Schema(description = "방문기록 전체개수", requiredMode = REQUIRED)
	long entireRecordCount,
	@Schema(description = "방문기록 리스트들", requiredMode = REQUIRED)
	List<AttractionRecordResponse> recordResponses
) {
}
