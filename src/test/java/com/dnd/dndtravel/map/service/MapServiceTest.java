package com.dnd.dndtravel.map.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dnd.dndtravel.map.domain.Opacity;
import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.repository.MapRepository;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class MapServiceTest {

	@InjectMocks
	private MapService sut;

	@Mock
	private MapRepository mapRepository;

	@Test
	void 전체_지역정보를_조회한다() {
		// given
		List<Region> mockRegions = List.of(
			Region.of("서울특별시", Opacity.TWO),
			Region.of("부산", Opacity.ZERO),
			Region.of("충청도", Opacity.THREE)
		);
		given(mapRepository.findAll()).willReturn(mockRegions);

		// when
		RegionResponse actual = sut.allRegions();

		// then
		assertThat(actual.regions().size()).isEqualTo(3);
		assertThat(actual.visitCount()).isEqualTo(2);
	}
}