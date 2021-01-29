package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import java.util.List;

public interface ItemService {

    void saveItem(Item item);

    List<Item> findItems();

    Item findOne(Long itemId);
}
