package graduation.project.no18.global.oauth.membertoken;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberRefreshToken is a Querydsl query type for MemberRefreshToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRefreshToken extends EntityPathBase<MemberRefreshToken> {

    private static final long serialVersionUID = 1251255160L;

    public static final QMemberRefreshToken memberRefreshToken = new QMemberRefreshToken("memberRefreshToken");

    public final StringPath memberAccountId = createString("memberAccountId");

    public final NumberPath<Long> memberRefreshTokenId = createNumber("memberRefreshTokenId", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    public QMemberRefreshToken(String variable) {
        super(MemberRefreshToken.class, forVariable(variable));
    }

    public QMemberRefreshToken(Path<? extends MemberRefreshToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberRefreshToken(PathMetadata metadata) {
        super(MemberRefreshToken.class, metadata);
    }

}

