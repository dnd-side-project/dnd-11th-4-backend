package com.dnd.dndtravel.map.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttraction is a Querydsl query type for Attraction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttraction extends EntityPathBase<Attraction> {

    private static final long serialVersionUID = 7154276L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttraction attraction = new QAttraction("attraction");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QRegion region;

    public QAttraction(String variable) {
        this(Attraction.class, forVariable(variable), INITS);
    }

    public QAttraction(Path<? extends Attraction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttraction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttraction(PathMetadata metadata, PathInits inits) {
        this(Attraction.class, metadata, inits);
    }

    public QAttraction(Class<? extends Attraction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.region = inits.isInitialized("region") ? new QRegion(forProperty("region")) : null;
    }

}

