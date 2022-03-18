package org.tnmk.practicejson.pro02resourcepathregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro02ResourcePathRegistryApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro02ResourcePathRegistryApplication.class, args);
  }
}
