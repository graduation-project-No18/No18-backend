package graduation.project.no18.global.oauth2.memberauth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberRefreshToken {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRefreshTokenId;

    @NotNull
    @Size(max = 64)
    private String userId;

    @NotNull
    @Size(max = 256)
    private String refreshToken;

    public MemberRefreshToken(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max=256) String refreshToken)
    {
        this.userId=userId;
        this.refreshToken=refreshToken;
    }


}
