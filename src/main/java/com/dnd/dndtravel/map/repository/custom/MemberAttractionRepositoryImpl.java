package com.dnd.dndtravel.map.repository.custom;

import static com.dnd.dndtravel.map.domain.QAttraction.attraction;
import static com.dnd.dndtravel.map.domain.QMemberAttraction.memberAttraction;
import static com.dnd.dndtravel.member.domain.QMember.member;

import java.util.List;
import java.util.Optional;

import com.dnd.dndtravel.map.domain.MemberAttraction;
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
	public Long maxCursor(long memberId) {
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
	public List<RecordProjection> findAttractionRecords(long memberId, long cursorNo, int displayPerPage) {
		return jpaQueryFactory.select(Projections.constructor(RecordProjection.class,
				memberAttraction.id,
				ExpressionUtils.as(JPAExpressions
					.select(memberAttraction.id.count())
					.from(memberAttraction)
					.where(memberAttraction.member.id.eq(member.id)), entireRecordCount
				),
				memberAttraction.memo,
				memberAttraction.localDate,
				memberAttraction.region,
				attraction
			))
			.from(memberAttraction)
			.join(member).on(memberAttraction.member.id.eq(member.id))
			.join(attraction).on(memberAttraction.attraction.id.eq(attraction.id))
			.where(memberAttraction.id.lt(cursorNo))
			.orderBy(memberAttraction.id.desc())
			.limit(displayPerPage)
			.fetch();
	}
}
