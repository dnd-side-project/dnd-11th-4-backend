package com.dnd.dndtravel.map.repository.custom;

import static com.dnd.dndtravel.map.domain.QAttraction.attraction;
import static com.dnd.dndtravel.map.domain.QMemberAttraction.memberAttraction;
import static com.dnd.dndtravel.member.domain.QMember.member;

import java.util.List;
import com.dnd.dndtravel.map.repository.dto.projection.RecordProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberAttractionRepositoryImpl implements MemberAttractionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

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
				memberAttraction.memo,
				memberAttraction.localDate,
				memberAttraction.region,
				attraction
			))
			.from(memberAttraction)
			.join(attraction).on(memberAttraction.attraction.id.eq(attraction.id))
			.where(
				memberAttraction.member.id.eq(memberId),
				ltCursorNo(cursorNo)
			)
			.orderBy(memberAttraction.id.desc())
			.limit(displayPerPage)
			.fetch();
	}

	@Override
	public Long entireVisitCount(long memberId) {
		return jpaQueryFactory.select(memberAttraction.id.count())
			.from(memberAttraction)
			.where(memberAttraction.member.id.eq(memberId))
			.fetchOne();
	}

	private BooleanExpression ltCursorNo(Long cursorNo) {
		if (cursorNo == null || cursorNo <= 0) {
			return null; // 조건 없이 최신 데이터부터 조회
		}
		return memberAttraction.id.lt(cursorNo);
	}
}
