package com.dnd.dndtravel.map.exception;

import com.amazonaws.SdkClientException;

public class PhotoDeleteFailException extends RuntimeException{
	private static final String MESSAGE = "s3 이미지 삭제 실패 [imagePath=%s]";

	public PhotoDeleteFailException(String existingFileName, SdkClientException e) {
		super(String.format(MESSAGE,existingFileName), e);
	}
}
