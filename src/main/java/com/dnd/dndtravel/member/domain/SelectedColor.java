package com.dnd.dndtravel.member.domain;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum SelectedColor {
	PINK("PINK"),
	ORANGE("ORANGE"),
	YELLOW("YELLOW"),
	GREEN("GREEN"),
	BLUE("BLUE"),
	PURPLE("PURPLE");

	private final String value;

	SelectedColor(String value) {
		this.value = value;
	}

	public static SelectedColor convertToEnum(String selectedColor) {
		return Arrays.stream(SelectedColor.values())
			.filter(color -> color.getValue().equalsIgnoreCase(selectedColor))
			.findAny().orElseThrow(() -> new RuntimeException("존재하지 않는 색상입니다"));
	}

	public static boolean isMatch(String color) {
		return Arrays.stream(SelectedColor.values())
			.anyMatch(selectedColor -> selectedColor.getValue().equals(color));
	}
}
