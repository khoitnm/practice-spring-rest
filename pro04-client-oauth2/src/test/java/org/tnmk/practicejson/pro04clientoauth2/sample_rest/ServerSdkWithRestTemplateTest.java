package org.tnmk.practicejson.pro04clientoauth2.sample_rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;
import org.springframework.web.client.ResourceAccessException;
import org.tnmk.practicejson.pro04clientoauth2.testinfra.BaseIntegrationTest;

@Slf4j
public class ServerSdkWithRestTemplateTest extends BaseIntegrationTest {

  @Test
  void whenRequestToANonExistingHost_thenConnectionTimeout() throws Exception {
    String nonExistingServer_BaseUrl = "http://1.100.100.1:81"; // this is a non-existing host.
    ServerSdkWithRestTemplate serverSdkWithRestTemplate = new ServerSdkWithRestTemplate(nonExistingServer_BaseUrl);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    try {
      serverSdkWithRestTemplate.welcome();
    } catch (ResourceAccessException ex) {
      log.info("Expect getting error `ResourceAccessException`: " + ex.getMessage());
    } finally {
      stopWatch.stop();
      log.info("Runtime (mills): " + stopWatch.getTotalTimeMillis());

      Assertions.assertEquals(
          ServerSdkWithRestTemplate.CONNECTION_TIMEOUT_MILLIS,
          stopWatch.getTotalTimeMillis(),
          200);
    }

  }


  @Test
  void serverConnectionTimeout() throws Exception {
    ServerSdkWithRestTemplate serverSdkWithRestTemplate = new ServerSdkWithRestTemplate(ClientAppController.DEFAULT_SERVER_BASE_URL);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      // Server side will take 10 seconds LONGER the actual read timeout.
      // It means the request will definitely be timeout.
      serverSdkWithRestTemplate.connectionTimeout(ServerSdkWithRestTemplate.SOCKET_READ_TIMEOUT_MILLIS + 5000);
      Assertions.fail("A ReadTimeout exception should happen here.");
    } catch (ResourceAccessException ex) {
      log.info("Expect getting error `ResourceAccessException`: " + ex.getMessage());
    } finally {
      stopWatch.stop();
      log.info("Runtime (mills): " + stopWatch.getTotalTimeMillis());

      Assertions.assertEquals(
          ServerSdkWithRestTemplate.SOCKET_READ_TIMEOUT_MILLIS,
          stopWatch.getTotalTimeMillis(),
          100);
    }

  }
}
