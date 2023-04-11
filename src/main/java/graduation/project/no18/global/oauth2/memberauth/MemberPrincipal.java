package graduation.project.no18.global.oauth2.memberauth;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.global.oauth2.enums.ProviderType;
import graduation.project.no18.global.oauth2.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberPrincipal implements OAuth2User, UserDetails, OidcUser {

    private final String userId;
    private final ProviderType providerType;
    private final Role role;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public static MemberPrincipal create(Member member) {

        MemberPrincipal memberPrincipal =
                MemberPrincipal.builder()
                        .userId(member.getAccountId())
                        .providerType(member.getProviderType())
                        .role(Role.MEMBER)
                        .authorities(Collections.singletonList(new SimpleGrantedAuthority(Role.MEMBER.getCode())))
                        .build();

        return memberPrincipal;
    }

    private void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }
    public static MemberPrincipal create(Member member, Map<String, Object> attributes) {
        MemberPrincipal memberPrincipal = create(member);
        memberPrincipal.setAttributes(attributes);

        return memberPrincipal;
    }


}
