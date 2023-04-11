package graduation.project.no18.global.oauth2.service;


import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.repository.MemberRepository;
import graduation.project.no18.global.oauth2.JwtMemberDetails;
import graduation.project.no18.global.oauth2.enums.ProviderType;
import graduation.project.no18.global.oauth2.enums.Role;
import graduation.project.no18.global.oauth2.exception.OAuthProviderMissMatchException;
import graduation.project.no18.global.oauth2.memberInfo.OAuth2MemberInfo;
import graduation.project.no18.global.oauth2.memberInfo.OAuth2MemberInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        try{
            return process(userRequest,user);
        } catch (AuthenticationException ex){
            throw new OAuth2AuthenticationException(ex.getMessage());
        } catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType =
                ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        //provider타입에 따라서 각각 다르게 userInfo가져온다. (가져온 필요한 정보는 OAuth2UserInfo로 동일하다)

        OAuth2MemberInfo memberInfo =
                OAuth2MemberInfoFactory.getOAuth2MemberInfo(providerType, user.getAttributes());

        Member member =
                memberRepository.findMemberByAccountId(memberInfo.getId())
                        .orElseGet(()->createMember(memberInfo, providerType));

        if (providerType != member.getProviderType()) {
            throw new OAuthProviderMissMatchException(
                    "Looks like you're signed up with " +
                            providerType +
                            " account. Please use your " +
                            member.getProviderType() +
                            " account to login."
            );
        }

        return new JwtMemberDetails(member, user.getAttributes());
    }
    private Member createMember(OAuth2MemberInfo memberInfo, ProviderType providerType){

        Member member = Member.builder()
                .accountId(memberInfo.getId())
                .email(memberInfo.getEmail())
                .nickname(memberInfo.getName())
                .profileImg(memberInfo.getImageUrl())
                .roleType(Role.MEMBER)
                .providerType(providerType).build();

        return memberRepository.save(member);
    }

}
