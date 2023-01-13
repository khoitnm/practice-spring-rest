package org.tnmk.practicejson.pro05globalerrorhandler.sample_rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tnmk.practicejson.pro05globalerrorhandler.common.tracing.TracingFilter.HTTP_HEADER_CORRELATION_ID;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.tnmk.practicejson.pro05globalerrorhandler.testinfra.BaseIntegrationTest;

import java.util.UUID;

@Slf4j
public class SampleWelcomeTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void success() throws Exception {
        String responseStr = mvc
            .perform(
                get("/api/welcome")
                    .param("name", "TheManWithNoName")
                    .param("age", "2")
                    .param("income", "350650.5")
            )
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        log.info("Response: {}", responseStr);
        //Note: when looking at the log, you should see MDC value that was set by TracingFiter.
        SampleWelcome sampleWelcome = objectMapper.convertValue(responseStr, SampleWelcome.class);
        Assertions.assertNotNull(sampleWelcome.getMessage());
    }

    @Test
    void test_WrongArgumentType() throws Exception {
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
    void test_JakartaValidationRule_Min() throws Exception {
        String responseStr = mvc
            .perform(
                get("/api/welcome")
                    .param("name", "Hi") // not enough characters, min length is 3. (defined by jakarta.validation.@Min)
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

    @Test
    void test_WrongHttpMethodType() throws Exception {
        String responseStr = mvc
            .perform(
                put("/api/welcome") // httpMethod shoud be `GET`, not `PUT`
                    .header(HTTP_HEADER_CORRELATION_ID, UUID.randomUUID().toString())
                    .param("name", "Hi there!")
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
