package org.tnmk.practicespringrest.server.item;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practicespringrest.server.model.Item;

import java.time.Instant;

@Service
public class ItemStory {
    public Item createItem(Item item) {
        item.setStatus("created_" + Instant.now());
        return item;
    }

    public Item updateItem(Item item) {
        item.setStatus("update_" + Instant.now());
        return item;
    }

    public Item findItemById(String itemId) {
        if (StringUtils.isEmpty(itemId)){
            return null;
        }else {
            Item item = new Item();
            item.setId(itemId);
            item.setName("Some stored item "+itemId);
            item.setStatus("stored");
            return item;
        }
    }
}
