package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Response;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public Response signUpUser(@RequestBody Member member) {

        Response response = new Response();

        try {
            memberService.join(member);
            response.setStatusCode(200);
            response.setMessage("회원가입을 성공적으로 완료했습니다.");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("회원가입을 하는 도중 오류가 발생했습니다.");
            response.setData(e.toString());
        }

        return response;
    }


    @GetMapping("/Response")
    public Response getJsonResponse(
            @RequestParam(value = "msg") String msg) {

        Response response  = new Response();
        response.setStatusCode(400);
        response.setMessage(msg);


        return response;
    }

    @GetMapping
    public String get() {
        return "ss";
    }
}
