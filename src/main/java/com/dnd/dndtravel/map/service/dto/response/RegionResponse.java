package com.dnd.dndtravel.map.service.dto.response;

import java.util.List;

import com.dnd.dndtravel.map.service.dto.RegionDto;

public record RegionResponse(
	List<RegionDto> regions,
	int visitCount,
	int totalCount
) {

	private static final int TOTAL_COUNT = 16; // 전체 지역구의 개수, 변경가능성이 낮아 16이라는 상수로 고정

	public RegionResponse(List<RegionDto> regions, int visitCount) {
		this(regions, visitCount, TOTAL_COUNT);
	}
}
