package com.dnd.dndtravel.map.controller.swagger;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.config.AuthenticationMember;
import com.dnd.dndtravel.map.controller.request.RecordRequest;
import com.dnd.dndtravel.map.controller.request.UpdateRecordRequest;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordResponse;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordsResponse;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "map", description = "지도 API")
public interface MapControllerSwagger {

	String STATUS_CODE_400_BODY_MESSAGE = "{\"message\":\"잘못된 요청입니다\"}";

	@Operation(
		summary = "인증된 사용자의 모든 지역 정보 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 조회",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			schema = @Schema(implementation = RegionResponse.class)
			)
		),
		@ApiResponse(responseCode = "400", description = "유저가 존재하지 않는경우",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
		),
	})
	@AuthenticationCommonResponse
	RegionResponse map(
		@Parameter(hidden = true)
		AuthenticationMember authenticationMember
	);


	@Operation(
		summary = "인증된 사용자의 방문 기록 저장"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 저장"),
		@ApiResponse(responseCode = "400", description = "잘못된 방문기록 값 입력, 지역이나 유저정보가 유효하지 않는경우",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
			),
	})
	@AuthenticationCommonResponse
	void memo(
		@Parameter(hidden = true) AuthenticationMember authenticationMember,
		@Parameter(description = "사진", schema = @Schema(type = "array", format = "binary")) List<MultipartFile> photos,
		@Parameter(description = "기록 요청 정보", required = true) RecordRequest recordRequest
	);

	@Operation(
		summary = "인증된 사용자의 방문 기록 조회"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 조회",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
			array = @ArraySchema(schema = @Schema(implementation = AttractionRecordResponse.class)))),
		@ApiResponse(responseCode = "400", description = "잘못된 방문기록 값 입력, 유저정보가 유효하지 않은경우",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
		),
	})
	@AuthenticationCommonResponse
	AttractionRecordsResponse findRecords(
		@Parameter(hidden = true)
		AuthenticationMember authenticationMember,
		@Parameter(description = "게시글의 ID값, 0 혹은 미입력시 가장최신 페이지 조회", example = "이전요청의 마지막 게시글ID가 7인경우 7로 요청시 다음 페이지 게시글 조회")
		long cursorNo,
		@Parameter(description = "페이지당 조회할 게시글 개수, 미입력시 10으로 지정")
		int displayPerPage
	);

	@Operation(
		summary = "인증된 사용자의 방문 기록 수정"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 수정"),
		@ApiResponse(responseCode = "400", description = "유저정보나 방문기록, 명소정보가 유효하지 않은경우",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
		),
	})
	@AuthenticationCommonResponse
	void updateRecord(
		@Parameter(hidden = true)
		AuthenticationMember authenticationMember,
		@Parameter(description = "방문기록 id값", required = true)
		long recordId,
		@Parameter(description = "수정요청한 사진", required = false)
		List<MultipartFile> photos,
		@Parameter(description = "수정요청 값", required = true)
		UpdateRecordRequest updateRecordRequest
	);

	@Operation(
		summary = "인증된 사용자의 방문 기록 삭제"
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "정상 삭제"),
		@ApiResponse(responseCode = "400", description = "삭제하려는 방문기록이 유효하지 않은경우",
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(example = STATUS_CODE_400_BODY_MESSAGE)
			)
		),
	})
	@AuthenticationCommonResponse
	void deleteRecord(
		@Parameter(hidden = true)
		AuthenticationMember authenticationMember,
		@Parameter(description = "방문기록 id값", required = true)
		long recordId
	);
}
