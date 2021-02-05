package jpabook.jpashop.service;

import jpabook.jpashop.domain.member.Authority;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username);
        if(member == null) {
            throw new UsernameNotFoundException(username + "-> 데이터 베이스에서 찾을 수 없습니다.");
        }

        return createUser(username,member);
    }

    private org.springframework.security.core.userdetails.User  createUser(String username, Member member) {

        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(userauthority -> new SimpleGrantedAuthority(userauthority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(member.getUsername(),
                member.getPassword(),
                grantedAuthorities);
    }
}
