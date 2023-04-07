package graduation.project.no18.domain.member;

import graduation.project.no18.global.oauth.enums.ProviderType;
import graduation.project.no18.global.oauth.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberTest {

    @Test
    public void createMemberTest(){
        //given
        String accountId = "test";
        String email = "test@test.com";
        String password = "1234";
        String nickname = "test";
        String profileImg = null;
        String introduction = "test";
        ProviderType providerType = ProviderType.LOCAL;
        RoleType roleType = RoleType.MEMBER;

        //when
        Member member = new Member(accountId, email, password, profileImg, nickname, introduction, providerType, roleType);

        //then
        assertThat(member.checkPassword("1234")).isEqualTo(true);
    }
}
