package graduation.project.no18.oauth.oauth2member;

import java.util.Map;

public abstract class OAuth2MemberInfo {
    protected Map<String, Object> attributes;

    public OAuth2MemberInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes(){
        return attributes;
    }

    public abstract String getEmail();

    public abstract String getNickname();

    public abstract String getProfileImg();
}
