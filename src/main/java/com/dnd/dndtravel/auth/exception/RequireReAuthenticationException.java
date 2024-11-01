package com.dnd.dndtravel.auth.exception;

public class RequireReAuthenticationException extends RuntimeException {
	private static final String MESSAGE = "Apple 계정 재인증이 필요합니다.";

	public RequireReAuthenticationException(Exception e) {
		super(MESSAGE, e);
	}
}
