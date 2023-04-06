package graduation.project.no18.oauth.oauth2member;

import java.util.Map;

public class GoogleOAuth2MemberInfo extends OAuth2MemberInfo {
    public GoogleOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return super.getAttributes();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getProfileImg() {
        return (String) attributes.get("picture");
    }
}
