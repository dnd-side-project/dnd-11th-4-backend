package com.dnd.dndtravel.map.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.dndtravel.map.service.MapService;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MapController {

	private final MapService mapService;

	@GetMapping("/maps")
	public RegionResponse map() {
		Long memberId = 1L;
		return mapService.allRegions(memberId);
	}
}
