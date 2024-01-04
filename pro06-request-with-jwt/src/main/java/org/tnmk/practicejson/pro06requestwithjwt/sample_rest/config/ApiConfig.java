package org.tnmk.practicejson.pro06requestwithjwt.sample_rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    @ConfigurationProperties(prefix = "api01")
    public ApiProperties apiProperties01() {
        return new ApiProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "api02")
    public ApiProperties apiProperties02() {
        return new ApiProperties();
    }
}
