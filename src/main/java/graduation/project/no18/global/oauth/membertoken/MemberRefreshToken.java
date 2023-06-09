package graduation.project.no18.global.oauth.membertoken;

import graduation.project.no18.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberRefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_refresh_token_id")
    private Long memberRefreshTokenId;

    @NotNull
    @Column(unique = true)
    private String memberAccountId;

    @Column(length = 256)
    @NotNull
    private String refreshToken;

    public MemberRefreshToken(String memberAccountId, String refreshToken){
        this.memberAccountId = memberAccountId;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken){
        if(this.refreshToken.equals(refreshToken)){
            return;
        }
        this.refreshToken = refreshToken;
    }
}
