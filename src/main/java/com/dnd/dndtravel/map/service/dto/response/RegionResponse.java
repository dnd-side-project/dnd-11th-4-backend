package com.dnd.dndtravel.map.service.dto.response;

import java.util.List;

import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.member.domain.SelectedColor;

public record RegionResponse(
	List<RegionDto> regions, // 지역별 opacity 정보, 땅 이름
	int visitCount, // 방문 지도 개수
	int totalCount, // 전체 땅 개수
	SelectedColor selectedColor // 선택된 컬러
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
