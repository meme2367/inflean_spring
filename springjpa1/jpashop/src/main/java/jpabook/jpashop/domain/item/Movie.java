package jpabook.jpashop.domain.item;

import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@DiscriminatorValue("M")//싱글테이블 구분 위해서
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
