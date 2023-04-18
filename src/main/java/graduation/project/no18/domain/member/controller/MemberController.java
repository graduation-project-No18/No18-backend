package graduation.project.no18.domain.member.controller;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.service.MemberService;
import graduation.project.no18.global.oauth.auth.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member user = memberService.getMember(principal.getUsername());

        return ApiResponse.success("user", user);
    }


}
