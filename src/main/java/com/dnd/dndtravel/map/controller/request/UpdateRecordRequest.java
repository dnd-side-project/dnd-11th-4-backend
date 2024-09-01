package com.dnd.dndtravel.map.controller.request;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.map.controller.request.validation.RegionCondition;
import com.dnd.dndtravel.map.controller.request.validation.RegionEnum;
import com.dnd.dndtravel.map.dto.RecordDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateRecordRequest(
	@RegionEnum(enumClass = RegionCondition.class)
	String region,

	@NotBlank(message = "명소 이름은 필수 입력 사항입니다.")
	@Pattern(regexp = "^[가-힣]+$", message = "명소 이름은 한글만 입력 가능합니다.")
	@Size(max = 50, message = "명소 이름은 50자 이내여야 합니다.")
	String attractionName,

	@Size(max = 25, message = "메모는 25자 이내여야 합니다.")
	String memo,

	@NotNull(message = "날짜는 필수 입력 사항입니다.")
	LocalDate localDate
) {
	public RecordDto toDto(List<MultipartFile> photos) {
		return RecordDto.builder()
			.region(this.region)
			.attractionName(this.attractionName)
			.photos(photos)
			.memo(this.memo)
			.dateTime(this.localDate)
			.build();
	}
}
