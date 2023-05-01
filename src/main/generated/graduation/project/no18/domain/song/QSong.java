package graduation.project.no18.domain.song;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSong is a Querydsl query type for Song
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSong extends EntityPathBase<Song> {

    private static final long serialVersionUID = 1875023797L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSong song = new QSong("song");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath octave = createString("octave");

    public final graduation.project.no18.domain.recommendation.QRecommendation recommendation;

    public final StringPath singer = createString("singer");

    public final StringPath title = createString("title");

    public final StringPath youtubeLink = createString("youtubeLink");

    public QSong(String variable) {
        this(Song.class, forVariable(variable), INITS);
    }

    public QSong(Path<? extends Song> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSong(PathMetadata metadata, PathInits inits) {
        this(Song.class, metadata, inits);
    }

    public QSong(Class<? extends Song> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.recommendation = inits.isInitialized("recommendation") ? new graduation.project.no18.domain.recommendation.QRecommendation(forProperty("recommendation"), inits.get("recommendation")) : null;
    }

}

