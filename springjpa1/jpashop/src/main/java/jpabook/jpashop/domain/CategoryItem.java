package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
@Entity
@Getter @Setter
public class CategoryItem {

    @Id
    @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="category_id")
    private Category category;

}
