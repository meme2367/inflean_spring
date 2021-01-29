package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.Salt;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberService {

    Long join(Member member);

    Member login(String username, String password);

    List<Member> findMembers();

    Member findOne(Long memberId);


}