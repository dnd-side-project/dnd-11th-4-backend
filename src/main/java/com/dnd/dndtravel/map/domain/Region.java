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
	private Opacity opacity; // 방문 횟수(색의 opacity)

	public static Region of(String name, Opacity opacity) {
		return new Region(name, opacity);
	}

	public boolean isVisited() {
		return opacity.isNotZero();
	}

	private Region(String name, Opacity opacity) {
		this.name = name;
		this.opacity = opacity;
	}
}
