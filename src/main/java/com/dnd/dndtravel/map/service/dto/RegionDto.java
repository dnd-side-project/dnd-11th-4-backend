package com.dnd.dndtravel.map.service.dto;

import com.dnd.dndtravel.map.domain.Region;

public record RegionDto(
	String name,
	int opacity
) {

	public static RegionDto from(Region region) {
		return new RegionDto(region.getName(), region.getOpacity().toInt());
	}
}
