package com.dnd.dndtravel.map.repository.custom;

import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;

public interface PhotoRepositoryCustom {

	List<AttractionPhotoProjection> findByRecordDtos(List<RecordProjection> dtos);
}
