package graduation.project.no18.global.oauth.membertoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {
    Optional<MemberRefreshToken> findByMemberAccountId(String accountId);
    Optional<MemberRefreshToken> findByMemberAccountIdAndRefreshToken(String accountId, String refreshToken);
}
