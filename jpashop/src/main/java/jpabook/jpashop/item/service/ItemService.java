package jpabook.jpashop.item.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    @Transactional
    public void save(Item item) {
        repository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = repository.findOne(itemId);

        // Setter 사용 보다는 의미있는 메서드를 사용해서 업데이트 하는게 좋다.
        /*
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        */

        // 의미있는 메서드를 만들어서 Setter 사용을 없앴다. 이게 더 나은 코드이다.
        findItem.update(name, price, stockQuantity);
        // @Transactional 에 의해서 커밋된다. 변경 감지에 의해서 데이터를 처리하는 방식!
    }

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Item findOne(Long id) {
        return repository.findOne(id);
    }
}
