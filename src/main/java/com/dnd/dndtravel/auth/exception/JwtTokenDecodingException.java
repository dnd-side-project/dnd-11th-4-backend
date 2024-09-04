package com.dnd.dndtravel.auth.exception;

import io.jsonwebtoken.JwtException;

public class JwtTokenDecodingException extends RuntimeException {
	private static final String MESSAGE = "Jwt 토큰 해독 실패";

	public JwtTokenDecodingException(JwtException e) {
		super(MESSAGE, e);
	}
}
