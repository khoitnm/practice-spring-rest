package org.tnmk.practicejson.pro07timeoutproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro07TimeoutProxyApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro07TimeoutProxyApplication.class, args);
  }
}
