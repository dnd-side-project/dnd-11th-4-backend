package com.dnd.dndtravel.map.repository.custom;

import static com.dnd.dndtravel.map.domain.QMemberAttraction.memberAttraction;
import static com.dnd.dndtravel.map.domain.QPhoto.photo;
import static com.dnd.dndtravel.member.domain.QMember.member;

import java.util.List;

import com.dnd.dndtravel.map.repository.dto.projection.AttractionPhotoProjection;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberAttractionRepositoryImpl implements MemberAttractionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final NumberPath<Long> entireRecordCount = Expressions.numberPath(Long.class, "entireRecordCount");

	/**
	 * select max(ma.id)
	 * from memberAttraction ma
	 */
	@Override
	public Long maxCursor(Long memberId) {
		return jpaQueryFactory.select(memberAttraction.id.max())
			.from(memberAttraction)
			.fetchOne();
	}

	/**
	 * select *
	 * from memberAttraction ma
	 * join member m on ma.memberId = m.id
	 * where {cursor.no} < ma.id
	 * order by ma.id desc
	 * limit {displayPerPage}
	 */

	@Override
	public List<RecordProjection> findAttractionRecords(Long memberId, Long cursorNo, int displayPerPage) {
		return jpaQueryFactory.select(Projections.constructor(RecordProjection.class,
				memberAttraction.id,
				ExpressionUtils.as(JPAExpressions
					.select(memberAttraction.id.count())
					.from(memberAttraction)
					.where(memberAttraction.member.id.eq(member.id)), entireRecordCount
				),
				memberAttraction.attractionName,
				memberAttraction.memo,
				memberAttraction.localDate,
				memberAttraction.region
			))
			.from(memberAttraction)
			.join(member).on(memberAttraction.member.id.eq(member.id))
			.where(memberAttraction.member.id.eq(memberId)
				.and(memberAttraction.id.gt(cursorNo)))
			.orderBy(memberAttraction.id.desc())
			.limit(displayPerPage)
			.fetch();
	}

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
			.join(photo.memberAttraction, memberAttraction)
			.where(photo.memberAttraction.id.in(memberAttractionIds))
			.fetch();
	}
}
