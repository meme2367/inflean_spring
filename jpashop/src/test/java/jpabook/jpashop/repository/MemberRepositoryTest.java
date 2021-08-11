package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  MemberService memberService;

  @Test
  public void 회원가입() {
    Member member = new Member();
    member.setName("memberA");

    Long memberId = memberService.join(member);
    Member findMember = memberRepository.findOne(memberId);

    assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void 중복_회원_예외() {
    Member member1 = new Member();
    member1.setName("memberA");

    Member member2 = new Member();
    member2.setName("memberA");

    assertThatThrownBy(() -> {
      memberService.join(member1);
      memberService.join(member2);
    }).isInstanceOf(IllegalStateException.class);

  }

}