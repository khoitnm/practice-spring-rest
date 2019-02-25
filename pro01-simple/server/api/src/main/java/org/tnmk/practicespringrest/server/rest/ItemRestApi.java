package org.tnmk.practicespringrest.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tnmk.practicespringrest.server.rest.dto.ServerItemDto;

/**
 * This interface will be rused by @FeignClient on client apps.
 */
@RequestMapping(ApiConstants.API_BASE)
public interface ItemRestApi {
    @PostMapping(path = "/items", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)

    //This is just an example, but we don't really rely on the status.CREATED anyway.
    // To make it simple for client app, just use HttpStatus.OK for all success actions.
    // I don't see using HttpStatus.CREATED brings any additional benefits for client app.
    // And remember, don't try to map APIs actions to CRUD, so don't try to map HttpStatus to CRUD's status response.
    @ResponseStatus(HttpStatus.CREATED)
    ServerItemDto createServerItem(@RequestBody ServerItemDto serverItem);

    @PutMapping(path = "/items", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    ServerItemDto updateServerItem(@RequestBody ServerItemDto serverItem);

    @GetMapping(path = "/items/{itemId}", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    ServerItemDto findServerItemById(@PathVariable Integer itemId);

    @DeleteMapping(path = "/items/{itemId}", consumes = ApiConstants.VERSION, produces = ApiConstants.VERSION)
    void deleteServerItemById(@PathVariable Integer itemId);

}
