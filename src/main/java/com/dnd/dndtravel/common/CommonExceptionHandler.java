package com.dnd.dndtravel.common;

import com.dnd.dndtravel.auth.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dnd.dndtravel.map.exception.MemberAttractionNotFoundException;
import com.dnd.dndtravel.map.exception.MemberNotFoundException;
import com.dnd.dndtravel.map.exception.PhotoDeleteFailException;
import com.dnd.dndtravel.map.exception.PhotoEmptyException;
import com.dnd.dndtravel.map.exception.PhotoInvalidException;
import com.dnd.dndtravel.map.exception.PhotoUploadFailException;
import com.dnd.dndtravel.map.exception.RegionNotFoundException;

import lombok.extern.slf4j.Slf4j;

//todo 예외클래스가 많아지면 해당클래스가 길어질것으로 예상, 개선필요해보이고 보안 때문에 상태코드별로 애매하게 동일한 메시지를 전달해주고, 스웨거 문서로 상세 오류를 전달해주는데 이 구조가 적절한건지 고민해봐야한다.
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
	private static final String BAD_REQUEST_MESSAGE = "잘못된 요청입니다";

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> runtimeException() {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<String> runtimeException(MemberNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}

	@ExceptionHandler(MemberAttractionNotFoundException.class)
	public ResponseEntity<String> runtimeException(MemberAttractionNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}

	@ExceptionHandler(RegionNotFoundException.class)
	public ResponseEntity<String> runtimeException(RegionNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}
	
	@ExceptionHandler(AppleTokenDecodingException.class)
	public ResponseEntity<String> runtimeException(AppleTokenDecodingException e) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body("토큰 인증에 실패했습니다");
	}

	@ExceptionHandler(AppleTokenRevokeException.class)
	public ResponseEntity<String> runtimeException(AppleTokenRevokeException e) {
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body("토큰 인증에 실패했습니다");
	}

	@ExceptionHandler(JwtTokenExpiredException.class)
	public ResponseEntity<String> runtimeException(JwtTokenExpiredException e) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body("토큰 인증에 실패했습니다");
	}

	@ExceptionHandler(JwtTokenDecodingException.class)
	public ResponseEntity<String> runtimeException(JwtTokenDecodingException e) {
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body("토큰 인증에 실패했습니다");
	}

	@ExceptionHandler(RefreshTokenInvalidException.class)
	public ResponseEntity<String> runtimeException(RefreshTokenInvalidException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}

	@ExceptionHandler(PhotoEmptyException.class)
	public ResponseEntity<String> runtimeException(PhotoEmptyException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}

	@ExceptionHandler(PhotoDeleteFailException.class)
	public ResponseEntity<String> runtimeException(PhotoDeleteFailException e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(INTERNAL_SERVER_ERROR_MESSAGE);
	}

	@ExceptionHandler(PhotoUploadFailException.class)
	public ResponseEntity<String> runtimeException(PhotoUploadFailException e) {
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(INTERNAL_SERVER_ERROR_MESSAGE);
	}

	@ExceptionHandler(PhotoInvalidException.class)
	public ResponseEntity<String> runtimeException(PhotoInvalidException e) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(BAD_REQUEST_MESSAGE);
	}


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> runtimeException(RuntimeException e) {
		log.error("runtimeException = {}", e);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(INTERNAL_SERVER_ERROR_MESSAGE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> runtimeException(Exception e) {
		log.error("exception = {}", e);
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(INTERNAL_SERVER_ERROR_MESSAGE);
	}
}
