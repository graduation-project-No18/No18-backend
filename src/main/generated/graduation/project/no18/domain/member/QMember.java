package graduation.project.no18.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1568056747L;

    public static final QMember member = new QMember("member1");

    public final graduation.project.no18.domain.Base.QBaseEntity _super = new graduation.project.no18.domain.Base.QBaseEntity(this);

    public final StringPath accountId = createString("accountId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final StringPath email = createString("email");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath introduction = createString("introduction");

    public final StringPath nickname = createString("nickname");

    public final StringPath octave = createString("octave");

    public final StringPath password = createString("password");

    public final StringPath profileImg = createString("profileImg");

    public final EnumPath<graduation.project.no18.global.oauth.type.ProviderType> providerType = createEnum("providerType", graduation.project.no18.global.oauth.type.ProviderType.class);

    public final ListPath<graduation.project.no18.domain.recording.Recording, graduation.project.no18.domain.recording.QRecording> recordingList = this.<graduation.project.no18.domain.recording.Recording, graduation.project.no18.domain.recording.QRecording>createList("recordingList", graduation.project.no18.domain.recording.Recording.class, graduation.project.no18.domain.recording.QRecording.class, PathInits.DIRECT2);

    public final EnumPath<graduation.project.no18.global.oauth.type.RoleType> roleType = createEnum("roleType", graduation.project.no18.global.oauth.type.RoleType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

