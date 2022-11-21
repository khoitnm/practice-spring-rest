package org.tnmk.practicejson.pro00clientapp.sample_rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicejson.pro00clientapp.sample_rest.model.ClientAppWelcome;

@RestController
public class ClientAppController {
  public static final String DEFAULT_SERVER_BASE_URL = "http://localhost:8080";
  private final String serverBaseUrl = DEFAULT_SERVER_BASE_URL;
  private final ServerSdk serverSdk;

  public ClientAppController(RestTemplateBuilder restTemplateBuilder) {
    this.serverSdk = new ServerSdk(restTemplateBuilder, serverBaseUrl);
  }

  @GetMapping("/client-app/welcome")
  public ClientAppWelcome welcome() {
    return new ClientAppWelcome(serverSdk.welcome());
  }
}
