package org.tnmk.practicejson.pro04simplefilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro04SimpleFilterApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro04SimpleFilterApplication.class, args);
  }
}
