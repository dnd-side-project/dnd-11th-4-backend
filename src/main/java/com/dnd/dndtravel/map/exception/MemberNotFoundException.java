package com.dnd.dndtravel.map.exception;

public class MemberNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 유저 [memberId=%s]";

	public MemberNotFoundException(long memberId) {
		super(String.format(MESSAGE, memberId));
	}
}
