package com.dnd.dndtravel.member.swagger;

import org.springframework.http.MediaType;

import com.dnd.dndtravel.config.AuthenticationMember;
import com.dnd.dndtravel.map.controller.swagger.AuthenticationCommonResponse;
import com.dnd.dndtravel.member.service.response.MyPageResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface MemberControllerSwagger {

	String STATUS_CODE_400_BODY_MESSAGE = "{\"message\":\"잘못된 요청입니다\"}";

	@Operation(
		summary = "인증된 사용자의 마이페이지"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 조회",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = MyPageResponse.class)
			)
		),
		@ApiResponse(responseCode = "400", description = "유저가 존재하지 않는경우",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
		),
	})
	@AuthenticationCommonResponse
	MyPageResponse myPage(
		@Parameter(hidden = true)
		AuthenticationMember authenticationMember
	);
}
