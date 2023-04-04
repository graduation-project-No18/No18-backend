package graduation.project.no18.oauth.exception;

import org.springframework.security.core.Authentication;

public class TokenValidFailedException extends RuntimeException {
    public TokenValidFailedException() {
        super("토큰 생성 실패");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
