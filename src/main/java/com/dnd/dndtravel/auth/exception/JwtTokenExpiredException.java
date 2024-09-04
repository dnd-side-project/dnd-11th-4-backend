package com.dnd.dndtravel.auth.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtTokenExpiredException extends RuntimeException {
	private static final String MESSAGE = "Jwt 토큰이 만료되었음";

	public JwtTokenExpiredException(ExpiredJwtException e) {
		super(MESSAGE, e);
	}
}
