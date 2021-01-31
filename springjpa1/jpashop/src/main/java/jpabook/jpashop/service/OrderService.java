package jpabook.jpashop.service;

public interface OrderService {
    Long order(Long memberId, Long itemId, int count);
    void cancelOrder(Long orderId);
}
