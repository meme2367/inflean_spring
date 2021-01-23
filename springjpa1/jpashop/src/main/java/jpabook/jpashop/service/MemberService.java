package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //컴포넌트 스캔의 대상이 됨 -> 자동 스프링 빈으로 등록
@Transactional(readOnly = true)//트랜잭션안에서 데이터 변함, //읽기에는 readOnly = true -> 성능 최적화
//@AllArgsConstructor //lombok, 필드가지고 memberreposiotry만들어줌
@RequiredArgsConstructor//final 필드에만 생성자 만들어줌
public class MemberService {

    /*
    필드 인젝션
    @Autowired
    private MemberRepository memberRepository;
     필드 주입의 단점 : 바꾸지 못함 -> setter 인젝션
    */



    /*
    setter 인젝션의 단점 :  .. -> 생성자 인젝션
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */

    /*
    final : 컴파일시점에 체크가능

    생성자 인젝션(생성자가 하나만 있을 경우 @Autowired 없이도 자동 주입 가능
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */
    private final MemberRepository memberRepository;


    //회원 가입
    @Transactional//읽기아님(우선권가진 트랜잭션)
    public Long join(Member member) {
        //중복회원있는지 체크
        validateDuplicateMember(member);
        //영속성 컨텍스트에 올리면 키값이 pk(id)로 들어가있어서 id꺼낼수 있음 (db에 들어가지않아도)
        memberRepository.save(member);
        return member.getId();//멤버 반환 x : "쿼맨드와 쿼리를 분리해라"
    }

    private void validateDuplicateMember(Member member) {
        // 중복회원
        // EXCEPTION
        //동일한 이름 두명이 동시에 가입하면? -> db에 멤버이름을 unique 제약조건걸기
        //실무에서는 검증 로직이 있어도 멀티 쓰레드 상황을 고려해서
        // 회원 테이블의 회원명 컬럼에 유니크 제 약 조건을 추가하는 것이 안전하다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
