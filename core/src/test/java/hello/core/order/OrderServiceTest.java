package hello.core.order;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

  MemberService memberService;
  OrderService orderService;

  @BeforeEach
  void before() {
    memberService = new MemberServiceImpl(memberRepository());
    orderService = new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  private DiscountPolicy discountPolicy() {
    return new FixDiscountPolicy();
  }

  private MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }

  @Test
  @DisplayName("주문하기")
  void order() {

    Long memberId = 1L;

    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);
    Order itemA = orderService.createOrder(memberId, "itemA", 10000);

    System.out.println(itemA);
    System.out.println(itemA.calculatePrice());
  }

}
