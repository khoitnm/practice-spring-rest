package org.tnmk.practicejson.pro02resourcepathregistry.sample_rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class SampleController {

  @GetMapping("/api/welcome")
  public SampleWelcome welcome() {
    return new SampleWelcome("Hi there "+ ZonedDateTime.now());
  }
}
