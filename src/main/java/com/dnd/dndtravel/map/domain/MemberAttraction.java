package com.dnd.dndtravel.map.domain;

import java.time.LocalDate;

import com.dnd.dndtravel.member.domain.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberAttraction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attraction_id")
	private Attraction attraction;

	private String memo; // 방문기록 메모
	private LocalDate localDate; // 방문 날짜
	private String region; // 지역
	private int photosCount; // 사진 개수, 필요한가?
	//todo 명소 이름도 필요할지도?

	@Builder
	private MemberAttraction(Member member, Attraction attraction, String memo, LocalDate localDate, String region,
		int photosCount) {
		this.member = member;
		this.attraction = attraction;
		this.memo = memo;
		this.localDate = localDate;
		this.region = region;
		this.photosCount = photosCount;
	}

	public static MemberAttraction of(Member member, Attraction attraction, String memo, LocalDate localDate,
		String region) {
		return MemberAttraction.builder()
			.member(member)
			.attraction(attraction)
			.memo(memo)
			.localDate(localDate)
			.region(region)
			.build();
	}
}
