package org.tnmk.practicejson.pro01simple.sample_rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class SampleController {

  @GetMapping("/api/welcome")
  public SampleWelcome welcome() {
    return new SampleWelcome("Hi there "+ ZonedDateTime.now());
  }

  @GetMapping("/api/longRun")
  public String timeout(@RequestParam(value = "runtimeInMills", defaultValue = "5000") Long runtimeInMills) throws InterruptedException {
    Thread.sleep(runtimeInMills);
    return "done";
  }
}
