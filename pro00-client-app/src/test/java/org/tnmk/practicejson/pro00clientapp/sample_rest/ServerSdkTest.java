package org.tnmk.practicejson.pro00clientapp.sample_rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.StopWatch;
import org.springframework.web.client.ResourceAccessException;
import org.tnmk.practicejson.pro00clientapp.testinfra.BaseIntegrationTest;

@Slf4j
public class ServerSdkTest extends BaseIntegrationTest {
  @Autowired
  private RestTemplateBuilder restTemplateBuilder;


  @Test
  void canSendRequestTo_ApiServerSuccessfully() throws Exception {
    String nonExistingServer_BaseUrl = "http://localhost:" + System.nanoTime();
    ServerSdk serverSdk = new ServerSdk(restTemplateBuilder, nonExistingServer_BaseUrl);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    try {
      serverSdk.welcome();
    } catch (ResourceAccessException ex) {
      log.info("Expect getting error `ResourceAccessException`: " + ex.getMessage());
    } finally {
      stopWatch.stop();
      log.info("Runtime (mills): " + stopWatch.getTotalTimeMillis());

      Assertions.assertEquals(
          ServerSdk.CONNECTION_TIMEOUT_MILLIS,
          stopWatch.getTotalTimeMillis(),
          200);
    }

  }


  @Test
  void serverConnectionTimeout() throws Exception {
    ServerSdk serverSdk = new ServerSdk(restTemplateBuilder, ClientAppController.DEFAULT_SERVER_BASE_URL);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      // Server side will take 10 seconds LONGER the actual connection timeout.
      // It means the request will definitely be timeout.
      serverSdk.connectionTimeout(ServerSdk.SOCKET_READ_TIMEOUT_MILLIS + 5000);
      Assertions.fail("A ReadTimeout/ConnectionTimeout should happen here.");
    } catch (ResourceAccessException ex) {
      log.info("Expect getting error `ResourceAccessException`: " + ex.getMessage());
    } finally {
      stopWatch.stop();
      log.info("Runtime (mills): " + stopWatch.getTotalTimeMillis());

      Assertions.assertEquals(
          ServerSdk.SOCKET_READ_TIMEOUT_MILLIS,
          stopWatch.getTotalTimeMillis(),
          100);
    }

  }
}
