package com.dnd.dndtravel.map.exception;

public class PhotoEmptyException extends RuntimeException{
	private static final String MESSAGE = "이미지가 존재하지 않음";

	public PhotoEmptyException() {
		super(MESSAGE);
	}
}
