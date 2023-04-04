package graduation.project.no18.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    MEMBER("ROLE_MEMBER", "일반 사용자"),
    ADMIN("ROLE_ADMIN", "관리자 권한");

    private final String code;
    private final String displayName;
}
