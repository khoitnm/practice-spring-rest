package org.tnmk.practicejson.pro05globalerrorhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro05GlobalErrorHandlerApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro05GlobalErrorHandlerApplication.class, args);
  }
}
