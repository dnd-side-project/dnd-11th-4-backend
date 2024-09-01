package com.dnd.dndtravel.map.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dnd.dndtravel.map.domain.Photo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PhotoService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucketName}")
	private String bucketName;

	public String upload(MultipartFile image) {
		if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
			throw new RuntimeException("유효하지 않은 이미지");
		}
		validateFileExtension(image.getOriginalFilename());
		return uploadImage(image);
	}

	public void deleteBeforePhoto(List<String> existingUrls) {
		for (String existingPhotoUrl : existingUrls) {
			// 기존 이미지 URL에서 파일 이름 추출
			String existingFileName = existingPhotoUrl.substring(existingPhotoUrl.lastIndexOf('/') + 1);;

			// S3에서 기존 이미지 삭제
			try {
				amazonS3.deleteObject(bucketName, existingFileName);
			} catch (SdkClientException e) {
				throw new RuntimeException("Failed to delete image from S3", e);
			}
		}
	}

	private String uploadImage(MultipartFile image) {
		try {
			return uploadImageToS3(image);
		} catch (IOException e) {
			throw new RuntimeException("이미지 업로드 예외"); // custom ex (S3Exception)
		}
	}

	private void validateFileExtension(String filename) {
		int lastDotIndex = filename.lastIndexOf(".");
		if (lastDotIndex == -1) {
			throw new RuntimeException("파일 확장자가 없음");
		}

		String extension = filename.substring(lastDotIndex + 1).toLowerCase();
		List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png");

		if (!allowedExtentionList.contains(extension)) {
			throw new RuntimeException("invalid file extension");
		}
	}

	private String uploadImageToS3(MultipartFile image) throws IOException {
		String originalFilename = image.getOriginalFilename(); //원본 파일 명
		String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명
		String s3FileName = UUID.randomUUID().toString().substring(0,10) + originalFilename; //변경된 파일 명

		try (InputStream inputStream = image.getInputStream()) {
			ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
			metadata.setContentType("image/" + extension.substring(1)); // "." 제거
			metadata.setContentLength(image.getSize());

			//S3로 putObject 할 때 사용할 요청 객체
			//생성자 : bucket 이름, 파일 명, byteInputStream, metadata
			PutObjectRequest putObjectRequest =
				new PutObjectRequest(bucketName, s3FileName, inputStream, metadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);

			//실제로 S3에 이미지 데이터를 넣는 부분이다.
			amazonS3.putObject(putObjectRequest); // put image to S3
		} catch (SdkClientException e) {
			throw new RuntimeException("Failed to upload image to S3", e);
		}

		return amazonS3.getUrl(bucketName, s3FileName).toString();
	}
}
