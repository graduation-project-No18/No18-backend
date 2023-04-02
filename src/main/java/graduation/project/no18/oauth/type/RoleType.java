package graduation.project.no18.oauth.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    MEMBER("ROLE_MEMBER", "일반 유저"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String code;
    private final String name;


}
