package com.dnd.dndtravel.auth.exception;

public class AppleTokenDecodingException extends RuntimeException {
	private static final String MESSAGE = "Apple 토큰 payload 해독실패";

	public AppleTokenDecodingException(Exception e) {
		super(MESSAGE, e);
	}
}
