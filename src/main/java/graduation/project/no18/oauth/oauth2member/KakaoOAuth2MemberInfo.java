package graduation.project.no18.oauth.oauth2member;

import java.util.Map;

public class KakaoOAuth2MemberInfo extends OAuth2MemberInfo {
    public KakaoOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("account_email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getProfileImg() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("thumbnail_image");
    }
}
