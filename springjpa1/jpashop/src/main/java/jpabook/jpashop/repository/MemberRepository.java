package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //entity 찾아줌, componentscan의 대상이 되는 annotation 중 하나
@RequiredArgsConstructor
public class MemberRepository {

    /*
    스프링 부트에서의 jpa 제공 기능 : @Autowired로 해도 영속성컨텍스트로 인식해줌
    -> @Autowired로 사용가능하다는 얘기는 생성자주입 가능하다는 얘기
    (스프링 data jpa 없으면 안됨)
    @PersistenceContext
    private EntityManager em;
    */
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class,id);
    }

    public List<Member> findAll() {
        //jpql
        //인자 : 쿼리, 반환타입
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }

    public Member findByUsername(String username) {
//        return em.createQuery(
            return em.createQuery("select m from Member m where m.username = :username",Member.class)
                    .setParameter("username",username)
                    .getSingleResult();
        //return em.find(Member.class,username);
    }

}
