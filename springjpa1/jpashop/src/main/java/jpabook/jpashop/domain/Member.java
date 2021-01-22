package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity //jpa가 관리할 객체
@Getter //lombok
@Setter //lombok
public class Member {

    @Id
    @GeneratedValue//자동생성
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded//내장타입
    private Address address;

    //Order테이블에 있는 member 필드
    //읽기전용
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
