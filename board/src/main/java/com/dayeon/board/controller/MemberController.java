package com.dayeon.board.controller;

import com.dayeon.board.domain.Member;
import com.dayeon.board.dto.Response;
import com.dayeon.board.dto.SignInDto;
import com.dayeon.board.dto.SignUpDto;
import com.dayeon.board.jwt.JwtTokenProvider;
import com.dayeon.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public Response signUpUser(@RequestBody SignUpDto signUpDto) {
        try {
            memberService.signUpUser(signUpDto);
            return new Response("success", "회원가입을 성공적으로 완료했습니다.", null);
        } catch (Exception e) {
            return new Response("error", "회원가입을 하는 도중 오류가 발생했습니다.", null);
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signin")
    public Response signInUser(@RequestBody SignInDto signInDto,
                               HttpServletRequest req,
                               HttpServletResponse res) {
        try {
            Member member = memberService.signInUser(signInDto.getUsername(), signInDto.getPassword());
            String token = jwtTokenProvider.generateToken(member);
            String refreshJwt = jwtTokenProvider.generateRefreshToken(member);

            List<String> tokenList = new ArrayList<>();
            tokenList.add(token);
            tokenList.add(refreshJwt);

            return new Response("success", "로그인에 성공했습니다.", tokenList);
        } catch (Exception e) {
            return new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
    }
}
