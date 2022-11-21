package org.tnmk.practicejson.pro01simple.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.tnmk.practicejson.pro01simple.testinfra.BaseIntegrationTest;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebConfigTest extends BaseIntegrationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void test_Resources_for_App01() throws Exception {
    // When
    String assetManifestContent = mvc
        .perform(get("/app01/asset-manifest.json"))

        // Then
        .andExpect(status().isOk())
        .andExpect(header().string("Cache-Control", "no-store"))
        .andReturn().getResponse().getContentAsString();

    String resourcePath = getResourcePathFromAssetManifestContent(assetManifestContent);

    // When
    mvc.perform(get("/app01" + resourcePath))

        // Then
        .andExpect(status().isOk());
  }

  @Test
  void test_Resources_for_App02() throws Exception {
    String responseStr = mvc
        .perform(get("/app02/asset-manifest.json")).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

    String resourcePath = getResourcePathFromAssetManifestContent(responseStr);

    mvc.perform(get("/app02" + resourcePath)).andExpect(status().isOk());
  }

  private String getResourcePathFromAssetManifestContent(String assetManifestContent) throws JsonProcessingException {
    Map<String, String> files = (Map) objectMapper.readValue(assetManifestContent, Map.class).get("files");
    String resourcePath = files.values().iterator().next();
    return resourcePath;
  }
}
