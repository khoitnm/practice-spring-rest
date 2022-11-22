package org.tnmk.practicejson.pro01simple.sample_rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class SampleController {

  @GetMapping("/api/welcome")
  public SampleWelcome welcome(HttpServletRequest httpRequest) {
    String protocol = httpRequest.getProtocol();
    log.info("protocol: "+protocol);
    return new SampleWelcome("Hi there "+ ZonedDateTime.now());
  }

  @GetMapping("/api/longRun")
  public String timeout(@RequestParam(value = "runtimeInMills", defaultValue = "5000") Long runtimeInMills) throws InterruptedException {
    Thread.sleep(runtimeInMills);
    return "done";
  }
}
