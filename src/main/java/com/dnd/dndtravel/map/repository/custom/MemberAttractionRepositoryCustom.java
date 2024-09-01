package com.dnd.dndtravel.map.repository.custom;

import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;

public interface MemberAttractionRepositoryCustom {
	Long maxCursor(long memberId);

	List<RecordProjection> findAttractionRecords(long memberId, long cursorNo, int displayPerPage);
}
