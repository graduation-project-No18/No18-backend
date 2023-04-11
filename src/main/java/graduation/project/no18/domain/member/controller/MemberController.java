package graduation.project.no18.domain.member.controller;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.domain.member.service.MemberService;
import graduation.project.no18.global.oauth2.JwtMemberDetails;
import graduation.project.no18.global.oauth2.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member user = memberService.getMember(principal.getUsername());

        return ApiResponse.success("user", user);
    }


}
