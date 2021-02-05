package com.dayeon.board.service;

import com.dayeon.board.domain.Member;
import com.dayeon.board.dto.SignUpDto;
import com.dayeon.board.repository.MemberRepository;
import com.dayeon.board.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService {

    @Autowired
    private SaltUtil saltUtil;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void signUpUser(SignUpDto signUpDto) {
        String password = signUpDto.getPassword();
        String salt = saltUtil.genSalt();
        Member member = new Member();
        member.setSalt(salt);
        member.setPassword(saltUtil.encodePassword(salt,password));
        member.setEmail(signUpDto.getEmail());
        member.setUsername(signUpDto.getUsername());
        memberRepository.save(member);
    }

    public Member signInUser(String id, String password) throws Exception{
        Member member = memberRepository.findByUsername(id);
        if(member==null) throw new Exception ("멤버가 조회되지 않음");
        String salt = member.getSalt();
        password = saltUtil.encodePassword(salt,password);
        if(!member.getPassword().equals(password))
            throw new Exception ("비밀번호가 틀립니다.");
        return member;
    }

}
