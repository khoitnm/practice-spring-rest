package org.tnmk.practicejson.pro01simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro01SimpleApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro01SimpleApplication.class, args);
  }
}
