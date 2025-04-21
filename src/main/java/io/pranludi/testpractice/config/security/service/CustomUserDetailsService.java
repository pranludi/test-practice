package io.pranludi.testpractice.config.security.service;

import io.pranludi.testpractice.member.Member;
import io.pranludi.testpractice.member.MemberService;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final MemberService memberService;

    public CustomUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberService.find(username);
        return new User(
            member.getId(),
            "password",
            Set.of(new SimpleGrantedAuthority("ROLE_USER")),
            true
        );
    }

}
