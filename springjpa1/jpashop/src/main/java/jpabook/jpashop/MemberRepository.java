package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository//entity 찾아줌, componentscan의 대상이 되는 annotation 중 하나
public class MemberRepository {

    @PersistenceContext//영속성 컨텍스트
    private EntityManager em;//엔티티매니저 주입

    public Long save(Member member){
        em.persist(member);
        return member.getId();//멤버 반환 x : "쿼맨드와 쿼리를 분리해라"
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
