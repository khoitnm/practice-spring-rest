package org.tnmk.practicespringrest.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tnmk.practicespringrest.server.model.Item;

/**
 * This interface will be rused by @FeignClient on client apps.
 */
@RequestMapping(ApiConstants.API_BASE)
public interface ItemRestApi {
    @PostMapping(path = "/items", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    @ResponseStatus(HttpStatus.CREATED)
    Item createItem(@RequestBody Item item);

    @PutMapping(path = "/items", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    Item updateItem(@RequestBody Item item);

    @GetMapping(path = "/items/{itemId}", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    Item findItemById(@PathVariable String itemId);
}
