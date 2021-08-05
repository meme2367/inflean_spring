package hello.core.order;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {

  @Autowired
  MemberService memberService;
  @Autowired
  OrderService orderService;

  @Test
  @DisplayName("주문하기")
  void order() {

    Long memberId = 1L;

    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);
    Order itemA = orderService.createOrder(memberId, "itemA", 20000);

    System.out.println(itemA);
    System.out.println(itemA.calculatePrice());
  }

}
