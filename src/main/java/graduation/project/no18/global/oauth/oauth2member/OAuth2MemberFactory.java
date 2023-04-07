package graduation.project.no18.global.oauth.oauth2member;

import graduation.project.no18.global.oauth.enums.ProviderType;

import java.util.Map;

public class OAuth2MemberFactory {

    public static OAuth2MemberInfo getOAuth2MemberInfo(ProviderType providerType, Map<String, Object> attributes){
        if(providerType == ProviderType.GOOGLE){
            return new GoogleOAuth2MemberInfo(attributes);
        }
        if(providerType == ProviderType.NAVER){
            return new NaverOAuth2MemberInfo(attributes);
        }
        if(providerType == ProviderType.KAKAO){
            return new KakaoOAuth2MemberInfo(attributes);
        }
        throw new IllegalArgumentException("Invalid ProviderType");
    }
}
