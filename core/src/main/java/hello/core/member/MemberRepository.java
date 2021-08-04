package hello.core.member;

import org.springframework.stereotype.Repository;

public interface MemberRepository {

  void save(Member member);

  Member findById(Long memberId);
}
