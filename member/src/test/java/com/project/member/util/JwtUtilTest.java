package com.project.member.util;

import com.project.member.domain.Member;
import com.project.member.domain.MemberRole;
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
public class JwtUtilTest {
    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void 토큰검증() {

        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("dayeon");
        member.setNickname("dayeonNickname");
        member.setPassword("dayeonpassword");
        member.setEmail("meme91322367@gmail.com");
        member.setRole(MemberRole.ROLE_USER);

        //when
        String token = jwtUtil.generateToken(member);

        //then
        assertEquals(jwtUtil.getMemberId(token),member.getId());
    }

    @Test
    public void 토큰만료검증() {

        //given
        Member member = new Member();
        member.setId(1L);
        member.setName("dayeon");
        member.setNickname("dayeonNickname");
        member.setPassword("dayeonpassword");
        member.setEmail("meme91322367@gmail.com");
        member.setRole(MemberRole.ROLE_USER);

        //when
        String token = jwtUtil.generateToken(member);

        //then
        assertTrue(!jwtUtil.isTokenExpired(token));
    }


    @Test
    public void 클레임검증() {

        //given
        String token = jwtUtil.doGenerateToken(1L,"USER",1000L * 60 * 24 * 2);

        //when
        Long id = jwtUtil.getMemberId(token);

        //then
        assertEquals(id, java.util.Optional.of(1L).get());
    }

}