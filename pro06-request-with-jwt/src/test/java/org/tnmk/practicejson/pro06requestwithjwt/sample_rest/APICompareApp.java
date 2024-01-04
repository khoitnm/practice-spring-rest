package org.tnmk.practicejson.pro06requestwithjwt.sample_rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.tnmk.practicejson.pro06requestwithjwt.testinfra.BaseIntegrationTest;

@ActiveProfiles("qa")
public class APICompareApp extends BaseIntegrationTest {

    @Autowired
    private APICaller apiCaller;
    @Test
    public void start() throws JsonProcessingException {
        Integer[] params = {779, 933, 934, 942, 982, 1011, 3139, 3193, 3248, 3281};
        apiCaller.main(params);
    }
}
