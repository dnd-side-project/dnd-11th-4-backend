package com.dnd.dndtravel.auth.controller.request.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = {ColorValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColorValidation {
	String message() default "invalid region";
	Class<?>[] groups() default {};    // 추가
	Class<? extends Payload>[] payload() default {};  // 추가
	Class<? extends java.lang.Enum<?>> enumClass();
}
