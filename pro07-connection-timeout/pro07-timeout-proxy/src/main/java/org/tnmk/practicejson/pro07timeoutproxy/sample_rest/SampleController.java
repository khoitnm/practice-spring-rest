package org.tnmk.practicejson.pro07timeoutproxy.sample_rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController
public class SampleController {

  @GetMapping("/api/welcome")
  public SampleWelcome welcome() {
    log.info("Welcome");
    return new SampleWelcome("Hi there " + ZonedDateTime.now());
  }

  @GetMapping("/api/longRun")
  public String timeout(@RequestParam(value = "runtimeInMills", defaultValue = "5000") Long runtimeInMills) throws InterruptedException {
    Thread.sleep(runtimeInMills);
    return "done";
  }
}
