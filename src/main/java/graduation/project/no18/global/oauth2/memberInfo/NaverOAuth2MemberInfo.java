package graduation.project.no18.global.oauth2.memberInfo;

import java.util.Map;

public class NaverOAuth2MemberInfo extends OAuth2MemberInfo {

    public NaverOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }


    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        System.out.println(response);

        return (String) response.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("name");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");

        if(response==null){
            return null;
        }

        return (String) response.get("imageUrl");
    }


}
