package org.tnmk.practicejson.pro06requestwithjwt.sample_rest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
    private String host;
    private String path;
}
