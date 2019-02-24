package org.tnmk.practicespringrest.server.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicespringrest.server.model.ServerItem;
import org.tnmk.practicespringrest.server.rest.ItemRestApi;

@RestController
public class ItemRestService implements ItemRestApi {
    private final ItemStory itemStory;

    @Autowired
    public ItemRestService(ItemStory itemStory) {
        this.itemStory = itemStory;
    }

    public ServerItem createServerItem(ServerItem serverItem) {
        return itemStory.createItem(serverItem);
    }

    public ServerItem updateServerItem(ServerItem serverItem) {
        return itemStory.updateItem(serverItem);
    }

    public ServerItem findServerItemById(String itemId) {
        return itemStory.findItemById(itemId);
    }
}
