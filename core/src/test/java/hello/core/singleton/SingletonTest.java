package hello.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest(classes = AppConfig.class)
public class SingletonTest {

  @Autowired
  ApplicationContext ac;

  @Test
  @DisplayName("스프링 없는 순수한 DI 컨테이너")
  void pureContainer() {
    MemberService memberService1 = new MemberServiceImpl(new MemoryMemberRepository());
    MemberService memberService2 = new MemberServiceImpl(new MemoryMemberRepository());

    System.out.println(memberService1);
    System.out.println(memberService2);
    assertThat(memberService1).isNotSameAs(memberService2);
  }

  @Test
  @DisplayName("싱글톤 패턴을 적용한 객체 사용")
  public void singletonServiceTest() {

    //new SingletonService 안됨 : private 생성자

    SingletonService singletonService1 = SingletonService.getInstance();
    SingletonService singletonService2 = SingletonService.getInstance();

    System.out.println("singletonService1 = " + singletonService1);
    System.out.println("singletonService2 = " + singletonService2);

    assertThat(singletonService1).isSameAs(singletonService2);

    singletonService1.logic();
  }

  @Test
  @DisplayName("스프링 컨테이너와 싱글톤")
  void springContainer() {

    MemberService memberService1 = ac.getBean("memberService", MemberService.class);
    MemberService memberService2 = ac.getBean("memberService", MemberService.class);

    assertThat(memberService1).isSameAs(memberService2);
  }
}
