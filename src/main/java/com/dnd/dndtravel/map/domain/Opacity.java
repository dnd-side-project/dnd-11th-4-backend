package com.dnd.dndtravel.map.domain;

public enum Opacity {
	ZERO(0), ONE(1), TWO(2), THREE(3);

	private final int value;

	Opacity(int value) {
		this.value = value;
	}

	public boolean isNotZero() {
		return this != ZERO;
	}

	public int toInt() {
		return this.value;
	}
}
