package org.tnmk.practicespringrest.server.item.mapper;

import org.springframework.stereotype.Component;
import org.tnmk.practicespringrest.server.item.model.Item;
import org.tnmk.practicespringrest.server.rest.dto.ServerItemDto;

@Component
public class ItemMapper extends BaseDtoConverter<Item, ServerItemDto> {
}
