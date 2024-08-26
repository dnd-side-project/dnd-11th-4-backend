package com.dnd.dndtravel.map.controller.request.validation;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegionValidator implements ConstraintValidator<RegionEnum, String> {

	private RegionEnum annotation;

	@Override
	public void initialize(RegionEnum constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String region, ConstraintValidatorContext context) {
		// 지역 입력안하면 예외
		if (region == null) {
			return false;
		}

		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues == null) {
			return false;
		}

		// 지역 Enum중 하나라도 해당되면 true
		return Arrays.stream(enumValues).anyMatch(enumValue -> RegionCondition.isMatch(region));
	}
}
