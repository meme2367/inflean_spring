package jpabook.jpashop.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @Transactional
  void save() {
    Member member = new Member();
    member.setUsername("memberA");

    Long savedId = memberRepository.save(member);
    Member findMember = memberRepository.find(savedId);

    assertThat(findMember).isEqualTo(member);

  }

}