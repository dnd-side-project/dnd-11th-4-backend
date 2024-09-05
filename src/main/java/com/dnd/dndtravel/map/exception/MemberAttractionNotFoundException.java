package com.dnd.dndtravel.map.exception;

public class MemberAttractionNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 방문기록 [memberAttractionId=%s]";

	public MemberAttractionNotFoundException(long memberAttractionId) {
		super(String.format(MESSAGE, memberAttractionId));
	}
}
