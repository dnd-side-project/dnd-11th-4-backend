package com.dnd.dndtravel.auth.exception;

public class RefreshTokenInvalidException extends RuntimeException {
	private static final String MESSAGE = "유효하지 않은 RefreshToken 토큰 [refreshToken=%s]";

	public RefreshTokenInvalidException(String refreshToken) {
		super(String.format(MESSAGE, refreshToken));
	}
}
