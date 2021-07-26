package hello.core.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

  MemberService memberService;

  @BeforeEach
  void before() {
    memberService = new MemberServiceImpl(new MemoryMemberRepository());
  }

  @Test
  @DisplayName("회원가입")
  void join() {
    //given

    Member member = new Member(1L, "memberA", Grade.VIP);

    //when
    memberService.join(member);
    Member findMember = memberService.findMember(1L);

    //then
    assertThat(member).isEqualTo(findMember);
  }

}
