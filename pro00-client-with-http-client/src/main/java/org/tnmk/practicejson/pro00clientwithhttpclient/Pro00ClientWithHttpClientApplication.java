package org.tnmk.practicejson.pro00clientwithhttpclient;

import org.tnmk.practicejson.pro00clientwithhttpclient.sample_rest.ServerSdkWithHttpClient;

import java.io.IOException;

public class Pro00ClientWithHttpClientApplication {

  public static void main(String[] args) throws IOException {
    ServerSdkWithHttpClient serverSdkWithHttpClient =
        new ServerSdkWithHttpClient(ServerSdkWithHttpClient.DEFAULT_SERVER_BASE_URL);
    serverSdkWithHttpClient.welcome();
  }
}
