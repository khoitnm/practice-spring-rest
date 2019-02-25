package org.tnmk.practicespringrest.server.item.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicespringrest.server.item.mapper.ItemMapper;
import org.tnmk.practicespringrest.server.item.model.Item;
import org.tnmk.practicespringrest.server.item.service.ItemService;
import org.tnmk.practicespringrest.server.rest.ItemRestApi;
import org.tnmk.practicespringrest.server.rest.dto.ServerItemDto;

import java.util.Optional;

@RestController
public class ItemRestService implements ItemRestApi {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemRestService(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    public ServerItemDto createServerItem(ServerItemDto serverItemDto) {
        Item item = itemMapper.toModel(serverItemDto);
        Item savedItem = itemService.createItem(item);
        return itemMapper.toDTO(savedItem);
    }

    public ServerItemDto updateServerItem(ServerItemDto serverItemDto) {
        Item item = itemMapper.toModel(serverItemDto);
        Item savedItem = itemService.updateItem(item);
        return itemMapper.toDTO(savedItem);
    }

    public ServerItemDto findServerItemById(Integer itemId) {
        Optional<Item> foundItemOptional = itemService.findItemById(itemId);
        return itemMapper.toDTO(foundItemOptional.orElse(null));
    }

    @Override
    public void deleteServerItemById(Integer itemId) {
        itemService.deleteItem(itemId);
    }
}
