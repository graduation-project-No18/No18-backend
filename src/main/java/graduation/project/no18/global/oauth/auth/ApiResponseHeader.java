package graduation.project.no18.global.oauth.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseHeader {
    private int code;
    private String message;
}
