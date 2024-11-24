package com.dnd.dndtravel.map.repository.custom;

import static com.dnd.dndtravel.map.domain.QMemberAttraction.memberAttraction;
import static com.dnd.dndtravel.map.domain.QPhoto.photo;

import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<AttractionPhotoProjection> findByRecordDtos(List<RecordProjection> recordDtos) {
		List<Long> memberAttractionIds = recordDtos.stream()
			.map(RecordProjection::getMemberAttractionId)
			.toList();

		return jpaQueryFactory.select(
				Projections.constructor(AttractionPhotoProjection.class,
					memberAttraction.id,
					photo.url))
			.from(photo)
			.where(photo.memberAttraction.id.in(memberAttractionIds))
			.fetch();
	}
}
