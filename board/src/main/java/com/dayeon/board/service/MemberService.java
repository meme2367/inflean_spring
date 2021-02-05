package com.dayeon.board.service;

import com.dayeon.board.domain.Member;
import com.dayeon.board.dto.SignUpDto;
import com.dayeon.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void signUpUser(SignUpDto signUpDto) {
        String password = signUpDto.getPassword();

        Member member = new Member();
        member.setPassword(passwordEncoder.encode(password));
        member.setEmail(signUpDto.getEmail());
        member.setUsername(signUpDto.getUsername());
        memberRepository.save(member);
    }

    @Transactional
    public Member signInUser(String id, String password) throws Exception{
        Member member = memberRepository.findByUsername(id);
        if(member==null) throw new Exception ("멤버가 조회되지 않음");
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new Exception("비밀번호가 틀립니다.");
        }
        return member;
    }

}
