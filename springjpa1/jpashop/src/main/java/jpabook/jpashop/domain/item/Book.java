package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")//싱글테이블 구분 위해서
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
