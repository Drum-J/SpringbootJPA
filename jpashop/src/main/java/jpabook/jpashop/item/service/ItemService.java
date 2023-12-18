package jpabook.jpashop.item.service;

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

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Item findOne(Long id) {
        return repository.findOne(id);
    }
}
