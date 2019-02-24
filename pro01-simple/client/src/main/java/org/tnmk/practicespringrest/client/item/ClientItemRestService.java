package org.tnmk.practicespringrest.client.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is just a convenient code for you to do manual test on browsers.<br/>
 * Never use the similar pattern on your production project!
 */
@RestController
public class ClientItemRestService {

    private final ClientItemService clientItemService;

    @Autowired
    public ClientItemRestService(ClientItemService clientItemService) {
        this.clientItemService = clientItemService;
    }

    @GetMapping("/items.create")
    public ClientItem createRandomItem() {
        return clientItemService.addRandomItem();
    }

    @GetMapping("/items.delete")
    public ClientItem deleteRandomItem() {
        return clientItemService.addRandomItem();
    }

}