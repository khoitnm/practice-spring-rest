package org.tnmk.practicejson.pro00clientapp.sample_rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.tnmk.practicejson.pro00clientapp.sample_rest.model.ClientAppWelcome;
import org.tnmk.practicejson.pro00clientapp.testinfra.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientAppControllerTest extends BaseIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void canSendRequestToClientAppSuccessfully() throws Exception {
    String responseStr = mvc
        .perform(get("/client-app/welcome")).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    ClientAppWelcome clientAppWelcome = objectMapper.convertValue(responseStr, ClientAppWelcome.class);
    Assertions.assertNotNull(clientAppWelcome.getMessage());
  }
}
