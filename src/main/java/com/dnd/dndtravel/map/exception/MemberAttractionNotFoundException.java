package com.dnd.dndtravel.map.exception;

public class MemberAttractionNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 방문기록 혹은 멤버 [memberAttractionId=%s] [memberId=%s]";

	public MemberAttractionNotFoundException(long memberAttractionId, long memberId) {
		super(String.format(MESSAGE, memberAttractionId, memberId));
	}
}
