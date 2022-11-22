package org.tnmk.practicejson.pro00clientwithhttpclient.sample_rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

@Slf4j
public class ServerSdkWithHttpClientTest {

  @Test
  void whenRequestToANonExistingHost_thenConnectionTimeout() throws Exception {
    String nonExistingServer_BaseUrl = "http://1.100.100.1:81"; // this is a non-existing host.
    ServerSdkWithHttpClient serverSdkWithHttpClient = new ServerSdkWithHttpClient(nonExistingServer_BaseUrl);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    try {
      serverSdkWithHttpClient.welcome();
    } catch (Exception ex) {
      log.info("Expect getting error", ex);
    } finally {
      stopWatch.stop();
      log.info("Runtime (mills): " + stopWatch.getTotalTimeMillis());

      Assertions.assertEquals(
          ServerSdkWithHttpClient.CONNECTION_TIMEOUT_MILLIS,
          stopWatch.getTotalTimeMillis(),
          200);
    }

  }


  @Test
  void serverConnectionTimeout() throws Exception {
    ServerSdkWithHttpClient serverSdkWithHttpClient =
        new ServerSdkWithHttpClient(ServerSdkWithHttpClient.DEFAULT_SERVER_BASE_URL);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    try {
      // Server side will take 10 seconds LONGER the actual read timeout.
      // It means the request will definitely be timeout.
      serverSdkWithHttpClient.connectionTimeout(ServerSdkWithHttpClient.SOCKET_READ_TIMEOUT_MILLIS + 5000);
      Assertions.fail("A ReadTimeout exception should happen here.");
    } catch (Exception ex) {
      log.info("Expect getting error: ", ex);
    } finally {
      stopWatch.stop();
      log.info("Runtime (mills): " + stopWatch.getTotalTimeMillis());

      Assertions.assertEquals(
          ServerSdkWithHttpClient.SOCKET_READ_TIMEOUT_MILLIS,
          stopWatch.getTotalTimeMillis(),
          100);
    }

  }
}
