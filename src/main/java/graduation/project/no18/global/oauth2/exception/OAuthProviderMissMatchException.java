package graduation.project.no18.global.oauth2.exception;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class OAuthProviderMissMatchException extends RuntimeException{
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }

    public OAuthProviderMissMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OAuthProviderMissMatchException(Throwable cause) {
        super(cause);
    }

}

