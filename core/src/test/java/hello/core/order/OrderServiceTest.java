package hello.core.order;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

  @Test
  @DisplayName("주문하기")
  void order() {

    Long memberId = 1L;
    MemberService memberService = new MemberServiceImpl(new MemoryMemberRepository());
    OrderService orderService = new OrderServiceImpl();

    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);
    Order itemA = orderService.createOrder(memberId, "itemA", 10000);

    System.out.println(itemA);
    System.out.println(itemA.calculatePrice());
  }

}
