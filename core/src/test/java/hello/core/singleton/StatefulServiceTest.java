package hello.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;


public class StatefulServiceTest {

  @Test
  @DisplayName("상태를 가진 싱글톤 빈 - 멀티스레드 동시성 문제")
  void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefullService statefullService1 = ac.getBean(StatefullService.class);
    StatefullService statefullService2 = ac.getBean(StatefullService.class);

    //Thread A : 사용자A 10000원 주문
    statefullService1.order1("userA", 10000);
    //Thread B : 사용자B 20000원 주문
    statefullService1.order1("userB", 20000);

    System.out.println("price = " + statefullService1.getPrice());

  }

  @Test
  @DisplayName("상태를 가진 싱글톤 빈 - 무상태로 만들어야")
  void statefulServiceSingleton2() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefullService statefullService1 = ac.getBean(StatefullService.class);
    StatefullService statefullService2 = ac.getBean(StatefullService.class);

    //Thread A : 사용자A 10000원 주문
    int a = statefullService1.order2("userA", 10000);
    //Thread B : 사용자B 20000원 주문
    int b = statefullService1.order2("userB", 20000);

    assertThat(a).isEqualTo(10000);

  }



  static class TestConfig {

    @Bean
    public StatefullService statefullService() {
      return new StatefullService();
    }
  }
}
