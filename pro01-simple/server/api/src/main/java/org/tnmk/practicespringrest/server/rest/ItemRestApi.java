package org.tnmk.practicespringrest.server.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tnmk.practicespringrest.server.rest.dto.ServerItemDto;

/**
 * This interface will be rused by @FeignClient on client apps.
 */
@RequestMapping(ApiConstants.API_BASE)
public interface ItemRestApi {
    @PostMapping(path = "/items")

    //This is just an example, but we don't really rely on the status.CREATED anyway.
    // To make it simple for client app, just use HttpStatus.OK for all success actions.
    // I don't see using HttpStatus.CREATED brings any additional benefits for client app.
    // And remember, don't try to map APIs actions to CRUD, so don't try to map HttpStatus to CRUD's status response.
    @ResponseStatus(HttpStatus.CREATED)
    ServerItemDto createServerItem(@RequestBody ServerItemDto serverItem);

    @PutMapping(path = "/items")
    ServerItemDto updateServerItem(@RequestBody ServerItemDto serverItem);

    /**
     * Note: Must explicitly declare variable name "itemId" inside @PathVariable("itemId")
     * Otherwise, you will get error "PathVariable annotation was empty on param 0."
     * @param itemId
     * @return
     */
    @GetMapping(path = "/items/{itemId}")
    ServerItemDto findServerItemById(@PathVariable("itemId") Integer itemId);

    @DeleteMapping(path = "/items/{itemId}")
    void deleteServerItemById(@PathVariable("itemId") Integer itemId);

}
