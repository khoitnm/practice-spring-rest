package org.tnmk.practicejson.pro07timeoutclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro07TimeoutClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro07TimeoutClientApplication.class, args);
  }
}
