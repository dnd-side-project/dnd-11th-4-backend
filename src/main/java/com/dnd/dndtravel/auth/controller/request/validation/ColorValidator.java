package com.dnd.dndtravel.auth.controller.request.validation;

import java.util.Arrays;

import com.dnd.dndtravel.map.controller.request.validation.RegionCondition;
import com.dnd.dndtravel.map.controller.request.validation.RegionEnum;
import com.dnd.dndtravel.member.domain.SelectedColor;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ColorValidator implements ConstraintValidator<ColorValidation, String> {

	private ColorValidation annotation;


	@Override
	public void initialize(ColorValidation constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String color, ConstraintValidatorContext context) {
		// 색상 없으면 예외
		if (color == null || color.isBlank()) {
			return false;
		}

		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues == null) {
			return false;
		}

		// 색상 Enum중 하나라도 해당되면 true
		return Arrays.stream(enumValues).anyMatch(enumValue -> SelectedColor.isMatch(color));
	}
}
