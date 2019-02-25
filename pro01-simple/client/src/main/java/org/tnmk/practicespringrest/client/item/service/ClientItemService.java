package org.tnmk.practicespringrest.client.item.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringrest.client.item.model.ClientItem;
import org.tnmk.practicespringrest.server.rest.dto.ServerItemDto;

@Service
public class ClientItemService {

    @Autowired
    private final ServerItemRestClient serverItemRestClient;

    public ClientItemService(ServerItemRestClient serverItemRestClient) {
        this.serverItemRestClient = serverItemRestClient;
    }

    public ClientItem addRandomItem(){
        ServerItemDto serverItem = new ServerItemDto();
        serverItem.setName("Item_"+System.nanoTime());
        ServerItemDto createdServerItem = serverItemRestClient.createServerItem(serverItem);

        ClientItem clientItem = new ClientItem();
        BeanUtils.copyProperties(createdServerItem, clientItem);
        return clientItem;
    }

    public void deleteItem(Integer itemId) {
        serverItemRestClient.deleteServerItemById(itemId);
    }
}
