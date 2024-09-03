package com.dnd.dndtravel.auth.controller.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.dnd.dndtravel.auth.controller.request.validation.ColorValidation;
import com.dnd.dndtravel.member.domain.SelectedColor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppleLoginRequest(
	@Schema(description = "authorization code", requiredMode = REQUIRED)
	@NotBlank(message = "authorization code는 필수 입니다.")
	@Size(max = 300, message = "authorization code 형식이 아닙니다")
	String appleToken,

	@Schema(description = "유저가 선택한 색상", requiredMode = REQUIRED)
	@ColorValidation(enumClass = SelectedColor.class)
	String selectedColor
){
}