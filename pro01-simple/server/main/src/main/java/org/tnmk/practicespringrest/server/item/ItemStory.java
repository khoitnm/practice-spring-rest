package org.tnmk.practicespringrest.server.item;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practicespringrest.server.model.ServerItem;

import java.time.Instant;

@Service
public class ItemStory {
    public ServerItem createItem(ServerItem serverItem) {
        serverItem.setStatus("created_" + Instant.now());
        return serverItem;
    }

    public ServerItem updateItem(ServerItem serverItem) {
        serverItem.setStatus("update_" + Instant.now());
        return serverItem;
    }

    public ServerItem findItemById(String itemId) {
        if (StringUtils.isEmpty(itemId)){
            return null;
        }else {
            ServerItem serverItem = new ServerItem();
            serverItem.setId(itemId);
            serverItem.setName("Some stored item "+itemId);
            serverItem.setStatus("stored");
            return serverItem;
        }
    }
}
