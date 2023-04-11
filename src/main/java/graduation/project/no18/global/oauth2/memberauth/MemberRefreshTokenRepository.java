package graduation.project.no18.global.oauth2.memberauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken,Long> {

    MemberRefreshToken findByUserId(String userId);
    MemberRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);


}
