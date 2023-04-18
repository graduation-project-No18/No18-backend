package graduation.project.no18.domain.member;

import graduation.project.no18.global.oauth.type.ProviderType;
import graduation.project.no18.global.oauth.type.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberTest {

    @Test
    public void checkPasswordTest(){
        //given
        String password = "1234";
        Member member = Member.builder()
                .password(password)
                .build();

        //when
        String s = "1234";

        //then
        assertThat(member.checkPassword(s)).isEqualTo(true);
    }

    @Test
    public void checkProviderTypeTest(){
        //given
        Member member = Member.builder()
                .providerType(ProviderType.LOCAL)
                .build();

        //when
        ProviderType pt1 = ProviderType.NAVER;
        ProviderType pt2 = ProviderType.LOCAL;

        //then
        assertThat(member.checkProviderType(pt1)).isEqualTo(false);
        assertThat(member.checkProviderType(pt2)).isEqualTo(true);
    }
}
