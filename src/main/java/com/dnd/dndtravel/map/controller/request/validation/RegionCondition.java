package com.dnd.dndtravel.map.controller.request.validation;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum RegionCondition {
	서울("서울"),
	부산("부산"),
	대구("대구"),
	인천("인천"),
	광주("광주"),
	대전("대전"),
	울산("울산"),
	경기도("경기도"),
	강원도("강원도"),
	충청북도("충청북도"),
	충남세종("충남·세종"),
	전라북도("전라북도"),
	전라남도("전라남도"),
	경상북도("경상북도"),
	경상남도("경상남도"),
	제주도("제주도");

	private final String value;

	RegionCondition(String value) {
		this.value = value;
	}

	public static boolean isMatch(String region) {
		return Arrays.stream(RegionCondition.values())
			.anyMatch(regionCondition -> regionCondition.getValue().equals(region));
	}
}
