package org.tnmk.practicejson.pro05globalerrorhandler.sample_rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.tnmk.practicejson.pro05globalerrorhandler.testinfra.BaseIntegrationTest;

@Slf4j
public class SampleWelcomeTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void welcomeSuccess() throws Exception {
        String responseStr = mvc
            .perform(
                get("/api/welcome")
                    .param("name", "TheManWithNoName")
                    .param("age", "2")
            )
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        log.info("Response: {}", responseStr);
        //Note: when looking at the log, you should see MDC value that was set by TracingFiter.
        SampleWelcome sampleWelcome = objectMapper.convertValue(responseStr, SampleWelcome.class);
        Assertions.assertNotNull(sampleWelcome.getMessage());
    }

    @Test
    void welcomeWrongArgumentType() throws Exception {
        String responseStr = mvc
            .perform(
                get("/api/welcome")
                    .param("name", "TheManWithNoname")
                    .param("age", "two")// cannot convert to number.
                    .param("income", "so rich")// cannot convert to double.
            )
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();

        //Note: when looking at the log, you should see MDC value that was set by TracingFiter.
        log.info("Response: {}", responseStr);
        SampleWelcome sampleWelcome = objectMapper.convertValue(responseStr, SampleWelcome.class);
        Assertions.assertNotNull(sampleWelcome.getMessage());
    }

    @Test
    void welcomeConstraintViolation() throws Exception {
        String responseStr = mvc
            .perform(
                get("/api/welcome")
                    .param("name", "Hi") // not enough characters, min length is 3.
                    .param("age", "2")
                    .param("income", "2.3")
            )
            .andExpect(status().isBadRequest())
            .andReturn().getResponse().getContentAsString();

        //Note: when looking at the log, you should see MDC value that was set by TracingFiter.
        log.info("Response: {}", responseStr);
        SampleWelcome sampleWelcome = objectMapper.convertValue(responseStr, SampleWelcome.class);
        Assertions.assertNotNull(sampleWelcome.getMessage());
    }
}
