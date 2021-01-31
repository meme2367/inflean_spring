package jpabook.jpashop.service.impl;


import jpabook.jpashop.domain.Salt;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.utils.SaltUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final SaltUtil saltUtil;


    @Override
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);
        String password = member.getPassword();
        String salt = saltUtil.genSalt();
        member.setSalt(new Salt(salt));
        member.setPassword(saltUtil.encodePassword(salt,password));
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    @Transactional
    public Long login(String username, String password) {

        Member member = validateExistMember(username);
        String salt = member.getSalt().getSalt();
        password = saltUtil.encodePassword(salt,password);
        if(!member.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    private Member validateExistMember(String username) {
        Member findMember = memberRepository.findByUsername(username);
        if(findMember == null) {
            throw new IllegalStateException("회원이 존재하지 않습니다.");
        }
        return findMember;
    }

    //회원 전체 조회
    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
