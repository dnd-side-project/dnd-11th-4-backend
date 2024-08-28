package com.dnd.dndtravel.map.service;

import com.dnd.dndtravel.map.domain.Region;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import com.dnd.dndtravel.map.domain.MemberRegion;
import com.dnd.dndtravel.map.repository.MemberRegionRepository;
import com.dnd.dndtravel.map.repository.RegionRepository;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;

@Disabled("테스트 코드는 추후 사용할 예정")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class MapServiceTest {

	@InjectMocks
	private MapService sut;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private RegionRepository regionRepository;

	@Mock
	private MemberRegionRepository memberRegionRepository;

	@ParameterizedTest
	@MethodSource("provideRegionsForTesting")
	void 전체_지역정보를_조회한다(List<Region> regions, int expectedVisitCount, int expectedRegionCount) {
		// given
		// given(mapRepository.findAll()).willReturn(regions);
		String a= "충남·세종";
		Member member = Member.of("유저1", "user12345@naver.com", "RED");
		Region region = Region.of("서울");
		given(memberRepository.findById(1L)).willReturn(Optional.of(member));
		given(memberRegionRepository.findByMemberId(1L)).willReturn(
			List.of(MemberRegion.of(member, region))
		);
		given(regionRepository.findAll()).willReturn(regions);

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