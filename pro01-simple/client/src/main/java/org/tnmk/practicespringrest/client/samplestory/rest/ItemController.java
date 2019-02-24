package org.tnmk.practicespringrest.client.samplestory.rest;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicespringrest.client.common.MDCConstants;
import org.tnmk.practicespringrest.client.samplestory.samplegrpcclient.Item;
import org.tnmk.practicespringrest.client.samplestory.samplegrpcclient.ItemId;

import java.util.UUID;

@RestController
public class ItemController {

    @RequestMapping("/items/{id}")
    public Item getItems(@PathVariable("id") String id) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put(MDCConstants.CORRELATION_ID, correlationId);
        ItemId itemId = new ItemId();
        itemId.setId(id);
        //TODO
        return null;
    }

}