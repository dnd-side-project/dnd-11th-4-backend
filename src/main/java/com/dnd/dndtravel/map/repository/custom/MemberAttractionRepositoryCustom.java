package com.dnd.dndtravel.map.repository.custom;

import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;

public interface MemberAttractionRepositoryCustom {

	Long maxCursor(Long memberId);

	List<RecordProjection> findAttractionRecords(Long memberId, Long cursorNo, int displayPerPage);

	List<AttractionPhotoProjection> findByRecordDtos(List<RecordProjection> dtos);
}
