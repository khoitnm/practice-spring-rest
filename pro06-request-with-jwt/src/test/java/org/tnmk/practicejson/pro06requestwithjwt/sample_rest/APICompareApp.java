package org.tnmk.practicejson.pro06requestwithjwt.sample_rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicejson.pro06requestwithjwt.testinfra.BaseIntegrationTest;

import java.util.List;

@ActiveProfiles("local")
public class APICompareApp extends BaseIntegrationTest {

    @Value("${params}")
    private List<Integer> params;

    @Autowired
    private APICaller apiCaller;
    @Test
    public void start() throws JsonProcessingException {
        apiCaller.main(params.toArray());
    }
}
