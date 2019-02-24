package org.tnmk.practicespringrest.server.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicespringrest.server.model.Item;
import org.tnmk.practicespringrest.server.rest.ItemRestApi;

@RestController
public class ItemRestService implements ItemRestApi {
    private final ItemStory itemStory;

    @Autowired
    public ItemRestService(ItemStory itemStory) {
        this.itemStory = itemStory;
    }

    public Item createItem(Item item) {
        return itemStory.createItem(item);
    }

    public Item updateItem(Item item) {
        return itemStory.updateItem(item);
    }

    public Item findItemById(String itemId) {
        return itemStory.findItemById(itemId);
    }
}
