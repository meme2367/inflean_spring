package hello.core.member;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {

  void join(Member member);

  Member findMember(Long memberId);
}
