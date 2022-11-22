
package org.tnmk.practicejson.pro00clientwithhttpclient.sample_rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * This class is considered as an SDK to call APIs on another server.
 */
@Slf4j
public class ServerSdkWithHttpClient {
  public static final String DEFAULT_SERVER_BASE_URL = "http://localhost:8080";

  /**
   * Reference: https://www.imperva.com/learn/performance/http-keep-alive/
   */
  public static final int CONNECTION_KEEP_ALIVE_MILLIS = 9 * 1000;
  /**
   * Time To Live: It's related a living time of a connection inside a pool before it's closed.
   * A note about the ConnectionTimeToLive implementation in Apache HttpClient 4.5.4: I think you must use the PoolingHttpClientConnectionManager for the option to work (it eventually all comes down to a call to this isExpired method). If you do not use this connection manager, test the option to make sure it really works.
   * https://stackoverflow.com/questions/31566851/apache-httpclient-setconnecttimeout-vs-setconnectiontimetolive-vs-setsock
   */
  public static final int CONNECTION_TIME_TO_LIVE_MILLIS = 11 * 1000;
  /**
   * A connection timeout is the maximum amount of time that the program is willing to wait to set up a connection to
   * another process.
   * You aren't getting or posting any application data at this point, just establishing the connection, itself.
   * Reference: https://stackoverflow.com/questions/7360520/connectiontimeout-versus-sockettimeout
   */
  public static final int CONNECTION_TIMEOUT_MILLIS = 5 * 1000;
  public static final int CONNECTION_REQUEST_TIMEOUT_MILLIS = 7 * 1000;

  /**
   * SocketTimeout:
   * A socket timeout is the timeout when waiting for individual packets.
   * It's a common misconception that a socket timeout is the timeout to receive the full response.
   * So if you have a socket timeout of 1 second, and a response comprised of 3 IP packets,
   * where each response packet takes 0.9 seconds to arrive, for a total response time of 2.7 seconds,
   * then there will be no timeout.
   * Reference: https://stackoverflow.com/questions/7360520/connectiontimeout-versus-sockettimeout
   */
  public static final int SOCKET_READ_TIMEOUT_MILLIS = 3 * 1000;
  private final String serverBaseUrl;
  private final HttpClient httpClient;

  public ServerSdkWithHttpClient(String serverBaseUrl) {
    this.serverBaseUrl = serverBaseUrl;
    this.httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(buildRequestConfig())
        .setDefaultSocketConfig(buildSocketConfig())
        .setKeepAliveStrategy(createConnectionKeepAliveStrategy(CONNECTION_KEEP_ALIVE_MILLIS))
        .setConnectionTimeToLive(CONNECTION_TIME_TO_LIVE_MILLIS, TimeUnit.MILLISECONDS)
        .setMaxConnTotal(-1)
        .build();
  }

  public String welcome() throws IOException {
    HttpUriRequest request = new HttpGet(serverBaseUrl + "/api/welcome");
    HttpResponse response = httpClient.execute(request);
    return readContent(response);
  }

  public String connectionTimeout(long serverRuntimeInMills) throws IOException {
    HttpUriRequest request = new HttpGet(serverBaseUrl + "/api/longRun?runtimeInMills=" + serverRuntimeInMills);
    HttpResponse response = httpClient.execute(request);
    return readContent(response);
  }

  private String readContent(HttpResponse response) throws IOException {
    return new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
  }


  /**
   * This method is copied from org.mitre.dsmiley.httpproxy.ProxyServlet#buildRequestConfig
   */
  private RequestConfig buildRequestConfig() {
    return RequestConfig.custom()
        .setRedirectsEnabled(false)
        .setCookieSpec("ignoreCookies")
        .setConnectTimeout(CONNECTION_TIMEOUT_MILLIS)
        .setSocketTimeout(SOCKET_READ_TIMEOUT_MILLIS)
        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS)
        .build();
  }

  /**
   * This method is copied from org.mitre.dsmiley.httpproxy.ProxyServlet#buildSocketConfig
   */
  private SocketConfig buildSocketConfig() {
    return SocketConfig.custom()
        .setSoTimeout(SOCKET_READ_TIMEOUT_MILLIS)
        .build();
  }

  private ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy(long keepAliveMillis) {
    return (httpResponse, httpContext) -> keepAliveMillis;
  }

}
