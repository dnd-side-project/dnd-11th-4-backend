package com.dnd.dndtravel.map.service;

import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.domain.VisitOpacity;
import com.dnd.dndtravel.map.repository.MapRepository;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class MapServiceTest {

	@InjectMocks
	private MapService sut;


	@ParameterizedTest
	@MethodSource("provideRegionsForTesting")
	void 전체_지역정보를_조회한다(List<Region> regions, int expectedVisitCount, int expectedRegionCount) {
		// given
		// given(mapRepository.findAll()).willReturn(regions);

		// when
		RegionResponse actual = sut.allRegions(1L);

		// then
		assertThat(actual.regions().size()).isEqualTo(expectedRegionCount);
		assertThat(actual.visitCount()).isEqualTo(expectedVisitCount);
	}

	private static Stream<Arguments> provideRegionsForTesting() {
		return Stream.of(
			// 빈 리스트
			Arguments.of(List.of(), 0, 0),

			// 모든 지역이 방문되지 않은 경우
			Arguments.of(List.of(
				Region.of("서울특별시"),
				Region.of("부산"),
				Region.of("충청도")
			), 0, 3),

			// 모든 지역이 방문된 경우
			Arguments.of(List.of(
				Region.of("서울특별시"),
				Region.of("부산"),
				Region.of("충청도")
			), 3, 3),

			// 일부 지역만 방문된 경우
			Arguments.of(List.of(
				Region.of("서울특별시"),
				Region.of("부산"),
				Region.of("충청도")
			), 2, 3)
		);
	}
}