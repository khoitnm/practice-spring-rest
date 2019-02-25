package org.tnmk.practicespringrest.client.item.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestService {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}