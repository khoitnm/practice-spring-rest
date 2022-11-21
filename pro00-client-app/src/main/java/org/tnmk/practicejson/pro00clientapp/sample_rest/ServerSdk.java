
package org.tnmk.practicejson.pro00clientapp.sample_rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * This class is considered as an SDK to call APIs on another server.
 */
public class ServerSdk {
  public static final int CONNECTION_TIMEOUT_MILLIS = 5 * 1000;
  public static final int SOCKET_READ_TIMEOUT_MILLIS = 3 * 1000;
  private final String serverBaseUrl;
  private final RestTemplate restTemplate;

  public ServerSdk(RestTemplateBuilder restTemplateBuilder, String serverBaseUrl) {
    this.serverBaseUrl = serverBaseUrl;
    this.restTemplate = restTemplateBuilder
        .setConnectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT_MILLIS))
        .setReadTimeout(Duration.ofSeconds(SOCKET_READ_TIMEOUT_MILLIS))
        .build();
  }

  public String welcome() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        serverBaseUrl + "/api/welcome",
        String.class);
    return response.getBody();
  }

  public String connectionTimeout(long serverRuntimeInMills) {
    ResponseEntity<String> response = restTemplate.getForEntity(
        serverBaseUrl + "/api/longRun?runtimeInMills=" + serverRuntimeInMills,
        String.class);
    return response.getBody();
  }
}
