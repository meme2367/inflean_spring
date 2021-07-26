package hello.core.member;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

  @Test
  @DisplayName("회원가입")
  void join() {
    //given
    MemberService memberService = new MemberServiceImpl(new MemoryMemberRepository());
    Member member = new Member(1L, "memberA", Grade.VIP);

    //when
    memberService.join(member);
    Member findMember = memberService.findMember(1L);

    //then
    assertThat(member).isEqualTo(findMember);
  }

}