package org.tnmk.practicejson.pro01simple.sample_rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.tnmk.practicejson.pro01simple.testinfra.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SampleWelcomeTest extends BaseIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void test_api() throws Exception {
    String responseStr = mvc
        .perform(get("/api/welcome")).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    SampleWelcome sampleWelcome = objectMapper.convertValue(responseStr, SampleWelcome.class);
    Assertions.assertNotNull(sampleWelcome.getMessage());
  }
}
