package hello.core.autowired;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

public class AllBeanTest {

  @Test
  @DisplayName("모든 빈 받아서 사용 + 다형성")
  void all() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class,
        AllBeanService.class);

    AllBeanService allBeanService = ac.getBean(AllBeanService.class);
    Member member = new Member(1L, "memberA", Grade.VIP);
    assertThat(allBeanService.discount(member, 20000, "fixDiscountPolicy")).isEqualTo(1000);
    assertThat(allBeanService.discount(member, 20000, "rateDiscountPolicy")).isEqualTo(2000);


  }

  @Service
  static class AllBeanService {

    Map<String, DiscountPolicy> discountPolicies;

    @Autowired
    public AllBeanService(Map<String, DiscountPolicy> discountPolicies) {
      this.discountPolicies = discountPolicies;
      System.out.println(discountPolicies);
    }

    public int discount(Member member, int count, String policy) {
      DiscountPolicy discountPolicy = discountPolicies.get(policy);
      return discountPolicy.discount(member, count);
    }
  }
}
