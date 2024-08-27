package com.dnd.dndtravel.map.controller.request.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Constraint(validatedBy = {RegionValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RegionEnum {
	String message() default "invalid region";
	Class<? extends java.lang.Enum<?>> enumClass();
}
