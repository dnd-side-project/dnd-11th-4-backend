package com.dnd.dndtravel.map.domain;

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
public class MemberRegion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	private int visitCount; // 방문 횟수

	@Builder
	private MemberRegion(Member member, Region region, int visitCount) {
		this.member = member;
		this.region = region;
		this.visitCount = visitCount;
	}

	public static MemberRegion of(Member member, Region region, int visitCount) {
		return MemberRegion.builder()
			.member(member)
			.region(region)
			.visitCount(visitCount)
			.build();
	}

	public boolean isVisited() {
		return this.visitCount > 0;
	}
}
