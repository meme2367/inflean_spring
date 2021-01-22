package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional//트랜잭
    @Rollback(false)//테스트끝난 후 롤백해버려 데이터가 없을테니 롤백하지못하게 세팅
    public void testMember(){
        //given
        Member member = new Member();
        member.setUsername("memberA");
        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);
        //then
        //assertj
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        //jpa 동일성 보장
        //true : 같은 트랜잭션 안에서 조회하므로, 같은 영속성 컨텍스트 안
        Assertions.assertThat(findMember).isEqualTo(member);
    }

}