package com.dnd.dndtravel.map.service.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.member.domain.SelectedColor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegionResponse(
	@Schema(description = "지역의 이름, opacity", requiredMode = REQUIRED)
	List<RegionDto> regions,

	@Schema(description = "방문 지역 개수", requiredMode = REQUIRED)
	int visitCount,

	@Schema(description = "전체 지역 개수", requiredMode = REQUIRED)
	int totalCount,

	@Schema(description = "유저가 선택한 색상", requiredMode = REQUIRED)
	SelectedColor selectedColor
) {

	private static final int TOTAL_COUNT = 16; // 전체 지역구의 개수, 변경가능성이 낮아 16이라는 상수로 고정
	private static final int DEFAULT_VISIT_COUNT = 0;

	public RegionResponse(List<RegionDto> regions, int visitCount, SelectedColor selectedColor) {
		this(regions, visitCount, TOTAL_COUNT, selectedColor);
	}

	public RegionResponse(List<RegionDto> regions, SelectedColor selectedColor) {
		this(regions, DEFAULT_VISIT_COUNT, TOTAL_COUNT, selectedColor);
	}
}
