package com.project.member.service;

import com.project.member.domain.Member;
import com.project.member.domain.MemberRole;
import com.project.member.dto.RequestJoinDTO;
import com.project.member.dto.RequestTokenDTO;
import com.project.member.repository.MemberRepository;
import com.project.member.util.CookieUtil;
import com.project.member.util.EncryptHelper;
import com.project.member.util.JwtUtil;
import com.project.member.util.RedisUtil;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final EncryptHelper encryptHelper;

    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    private final RedisUtil redisUtil;

    private final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Transactional
    public Long join(RequestJoinDTO requestJoinDTO) throws Exception {

        Member member = new Member();
        member.setNickname(requestJoinDTO.getNickname());
        member.setRole(MemberRole.ROLE_USER);
        member.setEmail(requestJoinDTO.getEmail());
        member.setPassword(encryptHelper.encrypt(requestJoinDTO.getPassword()));
        member.setName(requestJoinDTO.getName());

        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다."); }
    }

    public Member login(String nickname, String password) throws Exception {

        Member member = validateExistMemberByNickname(nickname);

        validateEncryptPassword(password,member.getPassword());

        return member;
    }

    private void validateEncryptPassword(String inputPassword,String hassedPassword) throws Exception {

        if(!encryptHelper.isMatch(inputPassword,hassedPassword)) {
            logger.info("password match false");
            throw new Exception("비밀번호가 틀립니다.");
        }

    }

    private Member validateExistMemberByNickname(String nickname) throws Exception {
        Member member = memberRepository.findByNickname(nickname);
        if(member == null) {
            throw new Exception("회원이 존재하지 않습니다.");
        }
        return member;
    }

    private Member validateExistMemberById(Long id) throws Exception {
        Member member = memberRepository.findOne(id);

        if (member == null ) {
            logger.info("user don't exist");
            throw new Exception("회원이 존재하지 않습니다.");
        } else if(member.getId() != id) {
            logger.info("user don't match");
            throw new Exception("디비에 있는 회원과 일치하지 않습니다.");
        }
        return member;
    }

    public String getNewToken(RequestTokenDTO requestTokenDTO) throws Exception {


        if (!jwtUtil.isTokenExpired(requestTokenDTO.getAccessToken())) {
            throw new Exception("만료되지 않은 accessToken입니다.");
        }

        String memberId = jwtUtil.getMemberId(requestTokenDTO.getRefreshToken());

        Long longMemberId = Long.parseLong(memberId);

        Member member = validateExistMemberById(longMemberId);

        if (jwtUtil.isTokenExpired(requestTokenDTO.getRefreshToken())) {
            logger.info("refreshToken expired");
            throw new Exception("만료된 refreshToken입니다. 다시 로그인하세요.");
        }


        String redisRefreshToken = redisUtil.getData(Long.toString(member.getId()));

        if (!requestTokenDTO.getRefreshToken().equals(redisRefreshToken)) {
            logger.info("redis값과 일치하지 않습니다.");
            throw new Exception("유효하지않은 refreshToken입니다.");
        }

        String newAccessToken = jwtUtil.generateToken(memberRepository.findOne(member.getId()));

        return newAccessToken;
    }

}
