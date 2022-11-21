package org.tnmk.practicejson.pro00clientapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro00ClientAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro00ClientAppApplication.class, args);
  }
}
