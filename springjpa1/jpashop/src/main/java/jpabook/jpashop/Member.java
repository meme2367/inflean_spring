package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //jpa가 관리할 객체
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue//자동생성
    private Long id;
    private String userName;
}
