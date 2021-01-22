package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    //Order - Member에서 연관관계의 주인
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //delivery - order 일대일 관계 : 둘 다 주인이 될 수 있다. -> 외래키랑 가까운 테이블로.
    @OneToOne(fetch = LAZY , cascade = ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    //시간분까지
    private LocalDateTime orderDate; //주문 시간

    @Enumerated(EnumType.STRING)//enumtype : ORDINAL은 숫자로 들어감
    private OrderStatus status; // 주문 상태(Order, Cancel)

    //연관관계 편의 메서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
