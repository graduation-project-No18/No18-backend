package graduation.project.no18.global.oauth.membertoken;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    Optional<MemberRefreshToken> findByMemberAccountId(String accountId);
    Optional<MemberRefreshToken> findByMemberAccountIdAndRefreshToken(String accountId, String refreshToken);
}
