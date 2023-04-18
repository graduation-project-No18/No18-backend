package graduation.project.no18.global.oauth.customservice;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.repository.MemberRepository;
import graduation.project.no18.global.oauth.principal.MemberPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByAccountId(id).orElseGet(() -> {
            throw new IllegalArgumentException("Can't find the id");
        });
        return MemberPrincipal.create(member);
    }
}
