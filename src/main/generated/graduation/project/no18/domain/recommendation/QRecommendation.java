package graduation.project.no18.domain.recommendation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendation is a Querydsl query type for Recommendation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendation extends EntityPathBase<Recommendation> {

    private static final long serialVersionUID = 1592850037L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendation recommendation = new QRecommendation("recommendation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final graduation.project.no18.domain.recording.QRecording recording;

    public final ListPath<graduation.project.no18.domain.song.Song, graduation.project.no18.domain.song.QSong> songList = this.<graduation.project.no18.domain.song.Song, graduation.project.no18.domain.song.QSong>createList("songList", graduation.project.no18.domain.song.Song.class, graduation.project.no18.domain.song.QSong.class, PathInits.DIRECT2);

    public QRecommendation(String variable) {
        this(Recommendation.class, forVariable(variable), INITS);
    }

    public QRecommendation(Path<? extends Recommendation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendation(PathMetadata metadata, PathInits inits) {
        this(Recommendation.class, metadata, inits);
    }

    public QRecommendation(Class<? extends Recommendation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recording = inits.isInitialized("recording") ? new graduation.project.no18.domain.recording.QRecording(forProperty("recording"), inits.get("recording")) : null;
    }

}

