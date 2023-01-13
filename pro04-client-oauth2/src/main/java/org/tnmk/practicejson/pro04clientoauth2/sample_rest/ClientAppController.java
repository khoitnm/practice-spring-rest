package org.tnmk.practicejson.pro04clientoauth2.sample_rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practicejson.pro04clientoauth2.sample_rest.model.ClientAppWelcome;

@RestController
public class ClientAppController {
  public static final String DEFAULT_SERVER_BASE_URL = "http://localhost:8080";
  private final String serverBaseUrl = DEFAULT_SERVER_BASE_URL;
  private final ServerSdkWithRestTemplate serverSdkWithRestTemplate;

  public ClientAppController() {
    this.serverSdkWithRestTemplate = new ServerSdkWithRestTemplate(serverBaseUrl);
  }

  @GetMapping("/client-app/welcome")
  public ClientAppWelcome welcome() {
    return new ClientAppWelcome(serverSdkWithRestTemplate.welcome());
  }
}
