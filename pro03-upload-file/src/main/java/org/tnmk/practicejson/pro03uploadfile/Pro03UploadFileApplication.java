package org.tnmk.practicejson.pro03uploadfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Pro03UploadFileApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro03UploadFileApplication.class, args);
  }
}
