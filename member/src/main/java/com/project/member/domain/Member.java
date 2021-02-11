package com.project.member.domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String email;
}
