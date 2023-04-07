package graduation.project.no18.global.oauth.principal;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.global.oauth.type.ProviderType;
import graduation.project.no18.global.oauth.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberPrincipal implements OAuth2User, UserDetails, OidcUser {

    private final String memberAccountId;
    private final String email;
    private final String password;
    private final ProviderType providerType;
    private final RoleType roleType;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getUsername() {
        return memberAccountId;
    }

    @Override
    public String getName() {
        return memberAccountId;
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

    private void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }
    public static MemberPrincipal create(Member member){
        return new MemberPrincipal(
                member.getAccountId(),
                member.getEmail(),
                member.getPassword(),
                member.getProviderType(),
                member.getRoleType(),
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.MEMBER.getCode()))
        );
    }


    public static MemberPrincipal create(Member member, Map<String, Object> attributes){
        MemberPrincipal memberPrincipal = create(member);
        memberPrincipal.setAttributes(attributes);
        return memberPrincipal;
    }
}
