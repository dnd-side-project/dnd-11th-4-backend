package com.dnd.dndtravel.map.controller.request.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {PhotoValidator.class})
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhotoValidation {
	String message() default "invalid photo size";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
