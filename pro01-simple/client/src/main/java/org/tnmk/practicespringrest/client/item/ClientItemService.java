package org.tnmk.practicespringrest.client.item;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringrest.server.model.ServerItem;

@Service
public class ClientItemService {

    @Autowired
    private final ServerItemRestClient serverItemRestClient;

    public ClientItemService(ServerItemRestClient serverItemRestClient) {
        this.serverItemRestClient = serverItemRestClient;
    }

    public ClientItem addRandomItem(){
        ServerItem serverItem = new ServerItem();
        serverItem.setName("Item_"+System.nanoTime());
        ServerItem createdServerItem = serverItemRestClient.createServerItem(serverItem);

        ClientItem clientItem = new ClientItem();
        BeanUtils.copyProperties(createdServerItem, clientItem);
        return clientItem;
    }
}
