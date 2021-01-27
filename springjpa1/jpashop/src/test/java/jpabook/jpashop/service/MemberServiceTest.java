package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//메모리모드로 디비까지 엮어서 테스트하는 방식 : spring과 integration
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional//테스트케이스에 있는 스프링 기본 트랜잭션 : 트랜잭션 커밋해서 디비에 안보내고 롤백함
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    // 영속성 컨텍스트가 flush를 안해서.
    // @Rollback(false) : 테스트끝난 후 트랜잭션은 롤백해버려 데이터가 없을테니 롤백하지못하게 함
    @Test
    public void 회원가입() throws Exception {
        //given 이런게 주어졌어
        Member member = new Member();
        member.setName("myeongdayeon");
        member.setPassword("yesyes");
        member.setUsername("myeong");
        member.setEmail("meme91322367@gmail.com");

        //when 이걸 실행하면
        Long saveId = memberService.join(member);

        //then 결과가 이게 나와야되
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test
    public void 로그인() throws Exception {

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예약() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim2");
        Member member2 = new Member();
        member2.setName("kim2");

        //when
        memberService.join(member1);
        memberService.join(member2);//여기서 중복회원있다고 예외가 터져야함
        /*
        try {
            memberService.join(member2);//여기서 중복회원있다고 예외가 터져야함
        } catch (IllegalStateException e){
            return; //예외가 떠야 정상적 성공
        }
        */

        //then
        //Assert의 fail : 코드가 돌다가 fail에 오면 안됨. fail에 오면 잘못된거임.
        fail("예외가 발생해야 한다.");

    }

}