package org.tnmk.practicejson.pro07timeoutserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro07TimeoutServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro07TimeoutServerApplication.class, args);
  }
}
