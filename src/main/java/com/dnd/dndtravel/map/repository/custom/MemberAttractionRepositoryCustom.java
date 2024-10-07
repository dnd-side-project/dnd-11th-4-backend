package com.dnd.dndtravel.map.repository.custom;

import java.util.List;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;

public interface MemberAttractionRepositoryCustom {
	Long maxCursor(long memberId);

	List<RecordProjection> findAttractionRecords(long cursorNo, int displayPerPage);

	Long entireVisitCount(long memberId);
}
