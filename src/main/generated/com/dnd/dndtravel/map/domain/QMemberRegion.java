package com.dnd.dndtravel.map.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberRegion is a Querydsl query type for MemberRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRegion extends EntityPathBase<MemberRegion> {

    private static final long serialVersionUID = -919485973L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberRegion memberRegion = new QMemberRegion("memberRegion");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.dnd.dndtravel.member.domain.QMember member;

    public final QRegion region;

    public final NumberPath<Integer> visitCount = createNumber("visitCount", Integer.class);

    public QMemberRegion(String variable) {
        this(MemberRegion.class, forVariable(variable), INITS);
    }

    public QMemberRegion(Path<? extends MemberRegion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberRegion(PathMetadata metadata, PathInits inits) {
        this(MemberRegion.class, metadata, inits);
    }

    public QMemberRegion(Class<? extends MemberRegion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.dnd.dndtravel.member.domain.QMember(forProperty("member")) : null;
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
    }

}

