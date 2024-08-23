package com.dnd.dndtravel.map.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // 지역 이름

	@Enumerated(EnumType.STRING)
	private VisitOpacity visitOpacity; // 방문 횟수(색의 opacity)

	public static Region of(String name, VisitOpacity visitOpacity) {
		return new Region(name, visitOpacity);
	}

	public boolean isVisited() {
		return visitOpacity.isNotZero();
	}

	private Region(String name, VisitOpacity visitOpacity) {
		this.name = name;
		this.visitOpacity = visitOpacity;
	}
}
