package com.dnd.dndtravel.map.exception;

public class RegionNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 지역 [region=%s]";

	public RegionNotFoundException(String region) {
		super(String.format(MESSAGE, region));
	}
}
