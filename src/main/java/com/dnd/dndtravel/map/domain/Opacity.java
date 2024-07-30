package com.dnd.dndtravel.map.domain;

public enum Opacity {
	ZERO, ONE, TWO, THREE;

	public boolean isNotZero() {
		return this != ZERO;
	}
}
