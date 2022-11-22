package org.tnmk.practicejson.pro00clientwithresttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro00ClientWithRestTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro00ClientWithRestTemplateApplication.class, args);
  }
}
