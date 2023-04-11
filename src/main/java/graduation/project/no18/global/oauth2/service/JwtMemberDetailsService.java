package graduation.project.no18.global.oauth2.service;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.repository.MemberRepository;
import graduation.project.no18.global.oauth2.JwtMemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member entityMember = memberRepository.findMemberByAccountId(username).orElseGet(()->{
            throw new UsernameNotFoundException("Could not find user with id : " + username);
        });
        return new JwtMemberDetails(entityMember);
    }
}
