package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    //Item - OrderItem 중 Orderitem이 연관관계 주인
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    //Order_item - Orders 중 order_item이 "다"관계, 연관관계의 주인
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;

    private int count;

}
