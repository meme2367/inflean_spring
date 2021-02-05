package com.dayeon.board.service;

import com.dayeon.board.domain.Member;
import com.dayeon.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username);

        if(member == null) {
            throw new UsernameNotFoundException(username + " : 사용자 존재하지 않음");
        }

        return new org.springframework.security.core.userdetails.User(
                member.getUsername()
                ,member.getPassword()
                , AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }
}
