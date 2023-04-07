package graduation.project.no18.domain.member.service;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMember(String memberAccountId){
        return memberRepository.findMemberByAccountId(memberAccountId).orElseGet(
                () -> {
                    throw new IllegalArgumentException("Invalid id");
                }
        );
    }
}
