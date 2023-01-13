package org.tnmk.practicejson.pro04clientoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro04ClientOauth2Application {

  public static void main(String[] args) {
    SpringApplication.run(Pro04ClientOauth2Application.class, args);
  }
}
