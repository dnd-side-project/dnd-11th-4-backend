package com.dnd.dndtravel.map.exception;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;

public class PhotoUploadFailException extends RuntimeException {
	private static final String MESSAGE = "s3 이미지 업로드 실패 [image=%s]";

	public PhotoUploadFailException(MultipartFile image, IOException e) {
		super(String.format(MESSAGE, image), e);
	}

	public PhotoUploadFailException(String image, SdkClientException e) {
		super(String.format(MESSAGE, image), e);
	}
}
