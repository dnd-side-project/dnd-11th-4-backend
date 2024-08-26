package com.dnd.dndtravel.map.controller.request.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {RegionValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegionEnum {
	String message() default "invalid region";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	Class<? extends java.lang.Enum<?>> enumClass();
}
