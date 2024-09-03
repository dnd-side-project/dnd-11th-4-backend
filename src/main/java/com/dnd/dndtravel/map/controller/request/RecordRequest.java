package com.dnd.dndtravel.map.controller.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dnd.dndtravel.map.controller.request.validation.RegionCondition;
import com.dnd.dndtravel.map.controller.request.validation.RegionEnum;
import com.dnd.dndtravel.map.dto.RecordDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public record RecordRequest(
	@Schema(description = "지역 이름", requiredMode = REQUIRED)
	@RegionEnum(enumClass = RegionCondition.class)
	String region,

	@Schema(description = "명소명", requiredMode = REQUIRED)
	@NotBlank(message = "명소명은 필수 입니다.")
	@Size(max = 10, message = "명소 이름은 10자 이내여야 합니다.")
	String attractionName,

	@Schema(description = "메모", requiredMode = NOT_REQUIRED)
	@Size(max = 25, message = "메모는 25자 이내여야 합니다.")
	String memo,

	@Schema(description = "방문날짜, ISO Date(yyyy-MM-dd) 형식으로 입력", requiredMode = NOT_REQUIRED)
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
