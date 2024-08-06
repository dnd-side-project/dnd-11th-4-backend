package com.dnd.dndtravel.map.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.dndtravel.map.service.MapService;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MapController {

	private final MapService mapService;

	@Tag(name = "MAP", description = "지도 API")
	@Operation(summary = "전체 지역 조회", description = "전체 지역 방문 횟수를 조회합니다.")
	@GetMapping("/maps")
	public RegionResponse map() {
		return mapService.allRegions();
	}
}
