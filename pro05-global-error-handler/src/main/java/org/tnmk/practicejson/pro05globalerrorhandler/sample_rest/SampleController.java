package org.tnmk.practicejson.pro05globalerrorhandler.sample_rest;

import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@Slf4j
@RestController
@Validated
public class SampleController {

    @GetMapping("/api/welcome")
    public SampleWelcome welcome(
        @RequestParam(value = "name") @Min(3) String name,
        @RequestParam(value = "age", required = false) Integer age,
        @RequestParam(value = "income", required = false) Double income
    ) {
        log.info("Welcome {}, {} year old, income {}", name, age, income);
        return new SampleWelcome("Hi there " + ZonedDateTime.now());
    }

    @GetMapping("/api/longRun")
    public String timeout(@RequestParam(value = "runtimeInMills", defaultValue = "5000") Long runtimeInMills) throws InterruptedException {
        Thread.sleep(runtimeInMills);
        return "done";
    }
}
