package com.dayeon.board.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.ROLE_NOT_PERMITTED;

    private String salt;

}
