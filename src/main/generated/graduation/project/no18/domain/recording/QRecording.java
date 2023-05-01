package graduation.project.no18.domain.recording;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecording is a Querydsl query type for Recording
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecording extends EntityPathBase<Recording> {

    private static final long serialVersionUID = 1226231451L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecording recording = new QRecording("recording");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final graduation.project.no18.domain.member.QMember member;

    public final ListPath<graduation.project.no18.domain.recommendation.Recommendation, graduation.project.no18.domain.recommendation.QRecommendation> recommendationList = this.<graduation.project.no18.domain.recommendation.Recommendation, graduation.project.no18.domain.recommendation.QRecommendation>createList("recommendationList", graduation.project.no18.domain.recommendation.Recommendation.class, graduation.project.no18.domain.recommendation.QRecommendation.class, PathInits.DIRECT2);

    public QRecording(String variable) {
        this(Recording.class, forVariable(variable), INITS);
    }

    public QRecording(Path<? extends Recording> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecording(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecording(PathMetadata metadata, PathInits inits) {
        this(Recording.class, metadata, inits);
    }

    public QRecording(Class<? extends Recording> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new graduation.project.no18.domain.member.QMember(forProperty("member")) : null;
    }

}

