package graduation.project.no18.global.oauth2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","비회원"),
    MEMBER("ROLE_MEMBER","회원"),

    ADMIN("ROLE_ADMIN","관리자");

    private String code;
    private String displayName;

    public static Role of(String code){
        return Arrays.stream(Role.values()).filter(r->r.getCode().equals(code)).findAny().orElse(GUEST);
    }



}
