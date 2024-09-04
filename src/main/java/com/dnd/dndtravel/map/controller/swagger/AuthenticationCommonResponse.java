package com.dnd.dndtravel.map.controller.swagger;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
	parameters = {
		@Parameter(
			description = "Bearer JWT 토큰",
			required = true,
			in = HEADER
		)
	}
)
@ApiResponses(value = {
	@ApiResponse(responseCode = "400", description = "토큰 헤더가 비어있거나, Bearer Token 형식이 아닌경우",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(example = "{\"message\":\"잘못된 요청입니다\"}")
		)
	),
	@ApiResponse(
		responseCode = "401", description = "토큰의 유저정보가 유효하지 않거나 토큰 해독 실패",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(example = "{\"message\": \"유효하지 않은 토큰값입니다.\"}")
		)
	),
	@ApiResponse(
		responseCode = "500", description = "서버 오류",
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(example = "{\"message\":\"Internal Server Error\"}")
		)
	)
})
public @interface AuthenticationCommonResponse {
}
