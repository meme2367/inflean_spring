

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import hellojpa.Member;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemberTest {

  EntityManager em;
  EntityManagerFactory emf;

  @Before
  public void init() {
    emf = Persistence.createEntityManagerFactory("hello");
    em = emf.createEntityManager();

  }

  @After
  public void close() {
    em.close();
    emf.close();

  }


  @Test
  public void 일차캐시_조회() {
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Member member = new Member();
    member.setId(13L);

    //1차캐시에 저장
    em.persist(member);

    //1차캐시에서 찾음
    em.find(Member.class, 13L);

    //DB에 올라감
    tx.commit();
  }

  @Test
  public void 동일성_보장() {
    Member member1 = em.find(Member.class, 1L);
    Member member2 = em.find(Member.class, 1L);
    assertSame(member1, member2);
  }

  @Test
  public void 쓰기_지연_저장소() {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Member member1 = new Member();
    member1.setId(15L);

    Member member2 = new Member();
    member2.setId(16L);

    System.out.println("=================");
    em.persist(member1);
    em.persist(member2);
    tx.commit();
  }

  @Test
  public void 플러시() {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Member member1 = new Member();
    member1.setId(17L);
    em.persist(member1);//1차 캐시에 올라감

    em.flush();//플러시 날림 : 트랜잭션 커밋되기 전에 DB에 반영
    System.out.println("=================");
    tx.commit();
  }
}
