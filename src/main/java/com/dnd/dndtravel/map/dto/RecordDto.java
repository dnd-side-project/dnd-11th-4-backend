package com.dnd.dndtravel.map.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
/**
 *
 */
@Builder
public record RecordDto(
	String region,
	String attractionName,
	List<MultipartFile> photos,
	String memo,
	LocalDate dateTime
) {
}
