package graduation.project.no18.global.oauth.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthReqDto {
    private String accountId;
    private String password;
}
