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

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String password;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private MemberRole status = MemberRole.ROLE_NOT_PERMITTED;

    @Embedded
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


    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
