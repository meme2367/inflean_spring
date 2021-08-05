package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {

    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    /* template callback
    Member member = memberRepository.findById(memberId);
    int discountPrice = template(member, itemPrice, price -> price * 10 / 100);
    */
    return new Order(memberId, itemName, itemPrice, discountPrice);

  }


  public int template(Member member, int price, DiscountCallBack discountCallBack) {
    int count = 0;
    if (member.getGrade() == Grade.VIP) {
      count = discountCallBack.discount(price);
    }

    return count;
  }

  private interface DiscountCallBack {
    int discount(int price);
  }
}
