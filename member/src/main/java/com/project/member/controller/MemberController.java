package com.project.member.controller;
import com.project.member.domain.Member;
import com.project.member.dto.RequestJoinDTO;
import com.project.member.dto.RequestLoginDTO;
import com.project.member.dto.RequestTokenDTO;
import com.project.member.dto.ResponseDTO;
import com.project.member.service.MemberService;
import com.project.member.util.CookieUtil;
import com.project.member.util.JwtUtil;
import com.project.member.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    private final CookieUtil cookieUtil;

    private final JwtUtil jwtUtil;

    private final RedisUtil redisUtil;

    private final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @GetMapping("/hello")
    public ResponseDTO hello() {
        return new ResponseDTO(200,"OK","");
    }


    @PostMapping("/join")
    public ResponseDTO join(@RequestBody RequestJoinDTO requestJoinDTO) {
        try {
            memberService.join(requestJoinDTO);
            return new ResponseDTO(200,"success",null);
        } catch (Exception e) {
            return new ResponseDTO(500,"error",null);
        }

    }


    @PostMapping("/login")
    public ResponseDTO login(@RequestBody RequestLoginDTO requestLoginDTO
            , HttpServletResponse response) {
        try {
            Member member = memberService.login(requestLoginDTO.getNickname(),requestLoginDTO.getPassword());

            String accessT = jwtUtil.generateToken(member);
            String refreshT = jwtUtil.generateRefreshToken(member);

            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshT);

            redisUtil.setDataExpire(Long.toString(member.getId()),refreshT, JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);

            response.addCookie(refreshToken);

            return new ResponseDTO(200,"OK",accessT);

        } catch (Exception e) {
            return new ResponseDTO(200,"OK","로그인에 실패했습니다.");
        }

    }

    @GetMapping("/newToken")
    public ResponseDTO getNewToken(@RequestBody RequestTokenDTO requestTokenDTO) throws Exception {
        try {
            String newAccessToken = memberService.getNewToken(requestTokenDTO);

            return new ResponseDTO(200,"success",newAccessToken);
        } catch (Exception e) {
            return new ResponseDTO(500,"error",null);
        }
    }


    @PostMapping("/logout")
    public ResponseDTO logout() {
        return new ResponseDTO(200,"OK","");
    }


}