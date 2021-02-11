package com.project.member.service;

import com.project.member.domain.Member;
import com.project.member.domain.MemberRole;
import com.project.member.dto.RequestJoinDTO;
import com.project.member.dto.RequestTokenDTO;
import com.project.member.repository.MemberRepository;
import com.project.member.util.JwtUtil;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtUtil jwtUtil;


    @Test
    public void 회원가입() throws Exception {
        //given
        RequestJoinDTO requestJoinDTO = new RequestJoinDTO();
        requestJoinDTO.setName("dayeon");
        requestJoinDTO.setPassword("welcome1");
        requestJoinDTO.setNickname("dayeon");
        requestJoinDTO.setEmail("meme91322367@gmail.com");

        //when
        Long saveId = memberService.join(requestJoinDTO);

        //then
        assertEquals(requestJoinDTO.getNickname(), memberRepository.findOne(saveId).getNickname());
    }

    @Test
    public void 로그인() throws Exception {
        //given
        RequestJoinDTO requestJoinDTO = new RequestJoinDTO();
        requestJoinDTO.setName("dayeon");
        requestJoinDTO.setPassword("dayeonPassword");
        requestJoinDTO.setNickname("dayeonNickname");
        requestJoinDTO.setEmail("meme91322367@gmail.com");
        Long saveId = memberService.join(requestJoinDTO);

        //when
        Long loginMemberId = memberService.login("dayeonNickname","dayeonPassword").getId();

        //then
        assertEquals(saveId,loginMemberId);

    }

    @Test
    public void 토큰재발급() throws Exception {
        RequestJoinDTO requestJoinDTO = new RequestJoinDTO();
        requestJoinDTO.setName("dayeon");
        requestJoinDTO.setPassword("dayeonPassword");
        requestJoinDTO.setNickname("dayeonNickname");
        requestJoinDTO.setEmail("meme91322367@gmail.com");
        Long saveId = memberService.join(requestJoinDTO);

        Member member = memberService.login("dayeonNickname","dayeonPassword");

        String accessT = jwtUtil.generateToken(member);
        String refreshT = jwtUtil.generateRefreshToken(member);

        RequestTokenDTO requestTokenDTO = new RequestTokenDTO();
        requestTokenDTO.setAccessToken(accessT);
        requestTokenDTO.setRefreshToken(refreshT);

        String newToken = memberService.getNewToken(requestTokenDTO);

        assertTrue(!jwtUtil.isTokenExpired(newToken));

    }


}