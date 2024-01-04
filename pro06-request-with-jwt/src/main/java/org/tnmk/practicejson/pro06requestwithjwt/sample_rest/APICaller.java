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
    private final ApiProperties apiProperties01;
    private final ApiProperties apiProperties02;
    private final OauthProperties oauthProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public void main(Object... params) throws JsonProcessingException {
        String token = getToken(restTemplate, objectMapper);
        if (token == null) {
            throw new IllegalStateException("Failed to get JWT token.");
        }

        for (Object param : params) {
            String apiUrl01 = String.format(apiProperties01.getHost() + apiProperties01.getPath(), param);
            String response01 = apiGet(restTemplate, apiUrl01, token);

            String apiUrl02 = String.format(apiProperties02.getHost() + apiProperties02.getPath());
            String requestBody = String.format(apiProperties02.getRequestBodyTemplate(), param);
            String response02 = apiPost(restTemplate, apiUrl02, token, requestBody);

            String response01AsList = "["+response01+"]";
            if (!response01AsList.equals(response02)) {
                log.error("Different response for param: {}", param);
                log.error("Response 01: {}", response01AsList);
                log.error("Response 02: {}", response02);
            }
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

    private static String apiGet(RestTemplate restTemplate, String apiUrl, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private static String apiPost(RestTemplate restTemplate, String apiUrl, String token, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        return response.getBody();
    }
}
