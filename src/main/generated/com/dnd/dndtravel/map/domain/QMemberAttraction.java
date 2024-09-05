package com.dnd.dndtravel.map.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAttraction is a Querydsl query type for MemberAttraction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAttraction extends EntityPathBase<MemberAttraction> {

    private static final long serialVersionUID = -155605282L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAttraction memberAttraction = new QMemberAttraction("memberAttraction");

    public final QAttraction attraction;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> localDate = createDate("localDate", java.time.LocalDate.class);

    public final com.dnd.dndtravel.member.domain.QMember member;

    public final StringPath memo = createString("memo");

    public final StringPath region = createString("region");

    public QMemberAttraction(String variable) {
        this(MemberAttraction.class, forVariable(variable), INITS);
    }

    public QMemberAttraction(Path<? extends MemberAttraction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAttraction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAttraction(PathMetadata metadata, PathInits inits) {
        this(MemberAttraction.class, metadata, inits);
    }

    public QMemberAttraction(Class<? extends MemberAttraction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attraction = inits.isInitialized("attraction") ? new QAttraction(forProperty("attraction"), inits.get("attraction")) : null;
        this.member = inits.isInitialized("member") ? new com.dnd.dndtravel.member.domain.QMember(forProperty("member")) : null;
    }

}

