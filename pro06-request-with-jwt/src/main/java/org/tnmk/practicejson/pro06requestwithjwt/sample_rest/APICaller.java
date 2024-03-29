package org.tnmk.practicejson.pro06requestwithjwt.sample_rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.tnmk.practicejson.pro06requestwithjwt.sample_rest.config.ApiProperties;
import org.tnmk.practicejson.pro06requestwithjwt.sample_rest.config.OauthProperties;
import org.tnmk.practicejson.pro06requestwithjwt.sample_rest.dto.TokenResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class APICaller {
    private static final int PARTITION_SIZE = 20;
    private final ApiProperties apiProperties01;
    private final ApiProperties apiProperties02;
    private final OauthProperties oauthProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public void main(Object... params) throws JsonProcessingException {
        List<List<Object>> partitionedLists = partitionList(Arrays.asList(params), PARTITION_SIZE);
        int partitionIndex = -1;
        for (List<Object> partition : partitionedLists) {
            partitionIndex++;
            try {
                checkResponseForItems(partitionIndex, partition.toArray());
            } catch (RuntimeException ex) {
                log.error("Cannot compare partition: {}", partition, ex);
            }
        }
    }

    private void checkResponseForItems(int partitionIndex, Object... params) throws JsonProcessingException {
        String token = getToken(restTemplate, objectMapper);
        if (token == null) {
            throw new IllegalStateException("Failed to get JWT token.");
        }
        String apiUrl02 = String.format(apiProperties02.getHost() + apiProperties02.getPath());

        List<String> allResponse01 = new ArrayList<>();
        int i = -1;
        int index;
        for (Object param : params) {
            i++;
            index = partitionIndex * PARTITION_SIZE + i;
            try {
                String apiUrl01 = String.format(apiProperties01.getHost() + apiProperties01.getPath(), param);
                String response01 = apiGet(restTemplate, apiUrl01, token);
                allResponse01.add(response01);

                String requestBody = String.format(apiProperties02.getRequestBodyTemplate(), param);
                String response02 = apiPost(restTemplate, apiUrl02, token, requestBody);

                String response01AsList = "[" + response01 + "]";
                assertSameResponse(index, param, response01AsList, response02);
            } catch (RuntimeException ex) {
                log.error("Cannot compare param: {}", param, ex);
            }
        }

        String paramsAsString = Arrays.stream(params).map(param -> String.valueOf(param)).collect(Collectors.joining(",", "", ""));
        String requestBody = String.format(apiProperties02.getRequestBodyTemplate(), paramsAsString);
        String response02Combined = apiPost(restTemplate, apiUrl02, token, requestBody);
        apiPost(restTemplate, apiUrl02, token, requestBody);

        String allResponse01AsJsonList = allResponse01.stream().collect(Collectors.joining(",", "[", "]"));
        assertSameResponse(-1, Arrays.toString(params), allResponse01AsJsonList, response02Combined);
    }

    private void assertSameResponse(int i, Object param, String response01, String response02) {
        if (!response01.equals(response02)) {
            log.warn("[{}] Different response for param: {}\n" +
                    "\t response01: {}\n" +
                    "\t response02: {}\n",
                i, param, response01, response02);
        } else {
            if (i == -1) {
                log.info("[{}] Same response for param: {}", i, param);
            } else {
                // don't need to print to avoid too many messages.
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
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            return ex.getResponseBodyAsString();
        }
    }

    private static String apiPost(RestTemplate restTemplate, String apiUrl, String token, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            return "[" + ex.getResponseBodyAsString() + "]";
        }
    }

    public static <T> List<List<T>> partitionList(List<T> inputList, int partitionSize) {
        List<List<T>> partitionedLists = new ArrayList<>();
        int listSize = inputList.size();

        for (int i = 0; i < listSize; i += partitionSize) {
            int end = Math.min(i + partitionSize, listSize);
            List<T> partition = inputList.subList(i, end);
            partitionedLists.add(partition);
        }

        return partitionedLists;
    }
}
