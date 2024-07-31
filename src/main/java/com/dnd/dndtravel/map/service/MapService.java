package com.dnd.dndtravel.map.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.repository.MapRepository;
import com.dnd.dndtravel.map.service.dto.RegionDto;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MapService {

	private final MapRepository mapRepository;

	@Transactional(readOnly = true)
	public RegionResponse allRegions() {
		List<Region> all = mapRepository.findAll();

		List<RegionDto> regions = all.stream()
			.map(RegionDto::from)
			.toList();
		int visitCount = (int)all.stream()
			.filter(Region::isVisited)
			.count();

		return new RegionResponse(regions, visitCount);
	}
}
