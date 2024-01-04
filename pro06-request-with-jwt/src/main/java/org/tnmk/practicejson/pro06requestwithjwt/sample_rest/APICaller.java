package org.tnmk.practicejson.pro06requestwithjwt.sample_rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tnmk.practicejson.pro06requestwithjwt.sample_rest.config.ApiProperties;
import org.tnmk.practicejson.pro06requestwithjwt.sample_rest.config.OauthProperties;
import org.tnmk.practicejson.pro06requestwithjwt.sample_rest.dto.TokenResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class APICaller {
    private final ApiProperties apiProperties;
    private final OauthProperties oauthProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public void main(Object... params) throws JsonProcessingException {
        String token = getToken(restTemplate, objectMapper);
        if (token == null) {
            throw new IllegalStateException("Failed to get JWT token.");
        }

        for (Object param : params) {
            String apiUrl = String.format(apiProperties.getHost() + apiProperties.getPath(), param);
            String response01 = api01_Get(restTemplate, apiUrl, token);
        }
    }

    private String getToken(RestTemplate restTemplate, ObjectMapper objectMapper) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the request body with the form parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("scope", oauthProperties.getScope());
        requestBody.add("client_id", oauthProperties.getClientId());
        requestBody.add("client_secret", oauthProperties.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(oauthProperties.getUrl(), requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            TokenResponse tokenData = objectMapper.readValue(response.getBody(), TokenResponse.class);
            return tokenData.getAccessToken();
        } else {
            throw new IllegalStateException("Failed to get JWT token. Status code: " + response.getStatusCodeValue());
        }
    }

    private static String api01_Get(RestTemplate restTemplate, String apiUrl, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
