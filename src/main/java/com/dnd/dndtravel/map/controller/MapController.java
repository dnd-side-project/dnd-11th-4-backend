package com.dnd.dndtravel.map.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.map.controller.request.RecordRequest;
import com.dnd.dndtravel.map.controller.request.UpdateRecordRequest;
import com.dnd.dndtravel.map.controller.request.validation.PhotoValidation;
import com.dnd.dndtravel.config.AuthenticationMember;
import com.dnd.dndtravel.map.service.MapService;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordDetailViewResponse;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordResponse;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
public class MapController {

	private final MapService mapService;

	@Tag(name = "MAP", description = "지도 API")
	@Operation(summary = "전체 지역 조회", description = "전체 지역 방문 횟수를 조회합니다.")
	@GetMapping("/maps")
	public RegionResponse map(AuthenticationMember authenticationMember) {
		return mapService.allRegions(authenticationMember.id());
	}

	@PostMapping("/maps/record")
	public void memo(
		@PhotoValidation @RequestPart("photos") List<MultipartFile> photos,
		@RequestPart("recordRequest") RecordRequest recordRequest,
		AuthenticationMember authenticationMember
	) {
		mapService.recordAttraction(recordRequest.toDto(photos), authenticationMember.id());
	}

	//커서를 0으로 주면 최신 게시글을을 displayPerPage별로 조회함
	//서버에서 클라에 마지막 게시글의 ID를 줘야함
	@GetMapping("/maps/history")
	public List<AttractionRecordResponse> findRecords(
		@RequestParam long cursorNo,
		@RequestParam(defaultValue = "10") int displayPerPage,
		AuthenticationMember authenticationMember
	) {
		return mapService.allRecords(authenticationMember.id(), cursorNo, displayPerPage);
	}

	// 기록 단건 조회
	@GetMapping("/maps/history/{recordId}")
	public AttractionRecordDetailViewResponse findRecord(
		@PathVariable long recordId
	) {
		long memberId = 1L;
		return mapService.findOneVisitRecord(memberId, recordId);
	}

	@PutMapping("/maps/history/{recordId}")
	public void updateRecord(
		@PathVariable long recordId,
		@PhotoValidation @RequestPart("photos") List<MultipartFile> photos,
		@RequestPart("updateRecordRequest") UpdateRecordRequest updateRecordRequest
	) {
		long memberId = 1L;
		mapService.updateVisitRecord(updateRecordRequest.toDto(photos), memberId, recordId);
	}
}
