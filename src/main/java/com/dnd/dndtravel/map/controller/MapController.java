package com.dnd.dndtravel.map.controller;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.config.AuthenticationMember;
import com.dnd.dndtravel.map.controller.request.RecordRequest;
import com.dnd.dndtravel.map.controller.request.UpdateRecordRequest;
import com.dnd.dndtravel.map.controller.request.validation.PhotoValidation;
import com.dnd.dndtravel.map.controller.swagger.MapControllerSwagger;
import com.dnd.dndtravel.map.service.MapService;
import com.dnd.dndtravel.map.service.dto.response.AttractionRecordResponse;
import com.dnd.dndtravel.map.service.dto.response.RegionResponse;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
public class MapController implements MapControllerSwagger {

	private final MapService mapService;

	@GetMapping("/maps")
	public RegionResponse map(AuthenticationMember authenticationMember) {
		return mapService.allRegions(authenticationMember.id());
	}

	@PostMapping(value = "/maps/record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void memo(
		AuthenticationMember authenticationMember,
		@PhotoValidation @RequestPart(value = "photos", required = false) List<MultipartFile> photos,
		@RequestPart("recordRequest") RecordRequest recordRequest
	) {
		mapService.recordAttraction(recordRequest.toDto(photos), authenticationMember.id());
	}

	//커서를 0으로 주면 최신 게시글을을 displayPerPage별로 조회함
	//서버에서 클라에 마지막 게시글의 ID를 줘야함
	@GetMapping("/maps/history")
	public List<AttractionRecordResponse> findRecords(
		AuthenticationMember authenticationMember,
		@RequestParam(defaultValue = "0") long cursorNo,
		@RequestParam(defaultValue = "10") int displayPerPage
	) {
		return mapService.allRecords(authenticationMember.id(), cursorNo, displayPerPage);
	}

	@PutMapping(value = "/maps/history/{recordId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void updateRecord(
		AuthenticationMember authenticationMember,
		@PathVariable long recordId,
		@PhotoValidation @RequestPart(value = "photos", required = false) List<MultipartFile> photos,
		@RequestPart("updateRecordRequest") UpdateRecordRequest updateRecordRequest
	) {
		mapService.updateVisitRecord(updateRecordRequest.toDto(photos), authenticationMember.id(), recordId);
	}

	// 기록 삭제
	@DeleteMapping("/maps/history/{recordId}")
	public void deleteRecord(
		AuthenticationMember authenticationMember,
		@PathVariable long recordId
	) {
		mapService.deleteRecord(authenticationMember.id(), recordId);
	}
}
