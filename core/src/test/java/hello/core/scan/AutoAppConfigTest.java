package hello.core.scan;

import static org.assertj.core.api.Assertions.assertThat;

import hello.core.CoreApplication;
import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AutoAppConfigTest {

  @Autowired
  MemberService memberService;

  @Test
  void basicScan() {

    assertThat(memberService).isInstanceOf(MemberService.class);
  }
}
