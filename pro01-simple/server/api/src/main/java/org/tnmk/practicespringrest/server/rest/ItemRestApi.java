package org.tnmk.practicespringrest.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tnmk.practicespringrest.server.model.ServerItem;

/**
 * This interface will be rused by @FeignClient on client apps.
 */
@RequestMapping(ApiConstants.API_BASE)
public interface ItemRestApi {
    @PostMapping(path = "/items", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    @ResponseStatus(HttpStatus.CREATED)
    ServerItem createServerItem(@RequestBody ServerItem serverItem);

    @PutMapping(path = "/items", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    ServerItem updateServerItem(@RequestBody ServerItem serverItem);

    @GetMapping(path = "/items/{itemId}", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    ServerItem findServerItemById(@PathVariable String itemId);
}
