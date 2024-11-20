package com.dnd.dndtravel.map.controller.request.validation;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhotoValidator implements ConstraintValidator<PhotoValidation, List<MultipartFile>> {

	@Override
	public boolean isValid(List<MultipartFile> photos, ConstraintValidatorContext context) {
		if (photos != null && photos.size() > 3) {
			return false;
		}
		return true;
	}
}
