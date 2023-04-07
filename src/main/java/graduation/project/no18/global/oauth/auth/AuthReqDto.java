package graduation.project.no18.global.oauth.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthReqDto {
    private String id;
    private String password;
}
