package jpabook.jpashop.domain.member;

import com.sun.istack.NotNull;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.Salt;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity //jpa가 관리할 객체
@Getter //lombok
@Setter //lombok
public class Member {

    @Id
    @GeneratedValue//자동생성
    @Column(name="member_id")
    private Long id;

    @NotNull
    private String password;

    @Column(unique = true)
    private String username;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)//enumtype : ORDINAL은 숫자로 들어감
    @Column(name = "role")
    private MemberRole status = MemberRole.ROLE_NOT_PERMITTED;//READY, COMP

    @Embedded//내장타입
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id")
    private Salt salt;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateAt;

    //Order테이블에 있는 member 필드
    //읽기전용
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
