package com.dnd.dndtravel.map.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.map.controller.request.RecordRequest;
import com.dnd.dndtravel.map.service.MapService;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MapController {

	private final MapService mapService;

	@Tag(name = "MAP", description = "지도 API")
	@Operation(summary = "전체 지역 조회", description = "전체 지역 방문 횟수를 조회합니다.")
	@GetMapping("/maps")
	public RegionResponse map() {
		Long memberId = 1L;
		return mapService.allRegions(memberId);
	}

	@PostMapping("/maps/record")
	public void memo(
		@RequestPart("photos") List<MultipartFile> photos,
		@RequestPart("recordRequest") RecordRequest recordRequest
	) {
		Long memberId = 1L;
		mapService.recordAttraction(recordRequest.toDto(photos), memberId);
	}
}
