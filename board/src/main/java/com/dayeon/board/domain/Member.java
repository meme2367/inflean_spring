package com.dayeon.board.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="member")
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private int id;

    private String username;

    private String email;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.ROLE_NOT_PERMITTED;

}
