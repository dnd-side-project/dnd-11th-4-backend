package com.dnd.dndtravel.map.service.dto.response;

import java.util.List;

import com.dnd.dndtravel.map.service.dto.RegionDto;

public record RegionResponse(
	List<RegionDto> regions,
	int visitCount
) {
}
