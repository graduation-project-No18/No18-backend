package graduation.project.no18.global.oauth.customservice;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.repository.MemberRepository;
import graduation.project.no18.global.oauth.oauth2member.OAuth2MemberInfoFactory;
import graduation.project.no18.global.oauth.oauth2member.OAuth2MemberInfo;
import graduation.project.no18.global.oauth.principal.MemberPrincipal;
import graduation.project.no18.global.oauth.type.ProviderType;
import graduation.project.no18.global.oauth.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest memberRequest) throws OAuth2AuthenticationException {
        OAuth2User member = super.loadUser(memberRequest);

        try{
            return this.process(memberRequest, member);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest memberRequest, OAuth2User member){
        ProviderType providerType = ProviderType.valueOf(
                memberRequest
                .getClientRegistration()
                        .getRegistrationId()
                        .toUpperCase());
        OAuth2MemberInfo memberInfo =
                OAuth2MemberInfoFactory.getOAuth2MemberInfo(providerType, member.getAttributes());

        Member savedMember =
                memberRepository.findMemberByAccountId(memberInfo.getId())
                        .orElseGet(() -> createMember(memberInfo, providerType));

        if(!savedMember.checkProviderType(providerType)){
            throw new IllegalArgumentException("소셜 로그인 계정을 잘못 고르지 않았나 확인해주세요!");
        }
        return MemberPrincipal.create(savedMember, member.getAttributes());
    }

    private Member createMember(OAuth2MemberInfo memberInfo, ProviderType providerType){
        Member member = Member.builder()
                .accountId(memberInfo.getId())
                .email(memberInfo.getEmail())
                .nickname(memberInfo.getNickname())
                .profileImg(memberInfo.getProfileImg())
                .providerType(providerType)
                .roleType(RoleType.MEMBER)
                .build();

        return memberRepository.saveAndFlush(member);
    }
}
