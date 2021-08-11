package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @Transactional
  @Rollback(false)
  void save() {
    Member member = new Member();
    member.setName("memberA");

    memberRepository.save(member);
    Member findMember = memberRepository.find(member.getId());

    assertThat(findMember).isEqualTo(member);

  }

}