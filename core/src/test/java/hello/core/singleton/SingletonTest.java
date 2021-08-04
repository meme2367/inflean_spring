package hello.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

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

    SingletonService singletonService1 = SingletonService.getInstance();

    SingletonService singletonService2 = SingletonService.getInstance();

    System.out.println("singletonService1 = " + singletonService1);
    System.out.println("singletonService2 = " + singletonService2);
    
    assertThat(singletonService1).isSameAs(singletonService2);

    singletonService1.logic();
  }
}
