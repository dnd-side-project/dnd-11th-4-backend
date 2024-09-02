package com.dnd.dndtravel.map.service.dto.response;

import java.time.LocalDate;

import com.dnd.dndtravel.map.domain.MemberAttraction;

import lombok.Builder;

@Builder
public record AttractionRecordDetailViewResponse(
	String region,
	String attractionName,
	String memo,
	LocalDate visitDate
) {
	public static AttractionRecordDetailViewResponse from(MemberAttraction memberAttraction) {
		return AttractionRecordDetailViewResponse.builder()
			.region(memberAttraction.getRegion())
			.attractionName(memberAttraction.getAttraction().getName()) //todo 이 구조가 좋을지, attraction.findById로 가져오는게 좋을지 고민해봐야 한다
			.memo(memberAttraction.getMemo())
			.visitDate(memberAttraction.getLocalDate())
			.build();
	}
}
