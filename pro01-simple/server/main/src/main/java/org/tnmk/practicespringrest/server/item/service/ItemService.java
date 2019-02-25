package org.tnmk.practicespringrest.server.item.service;

import org.springframework.stereotype.Service;
import org.tnmk.practicespringrest.server.item.model.Item;
import org.tnmk.practicespringrest.server.item.repository.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Item item) {
        if (item.getId() == null){
            throw new IllegalArgumentException("Item id must be not null" + item);
        }
        return itemRepository.save(item);
    }

    public Optional<Item> findItemById(Integer itemId) {
        return itemRepository.findById(itemId);
    }

    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }
}
