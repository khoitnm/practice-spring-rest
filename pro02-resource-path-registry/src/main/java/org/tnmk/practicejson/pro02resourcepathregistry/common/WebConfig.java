package org.tnmk.practicejson.pro02resourcepathregistry.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private static final int STATIC_RESOURCE_CACHE_IN_SECONDS = 3600; //3600 = 1 hour. 31536000 = 1 year

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //APP 01
    // PathPattern:
    //  - Change Path Pattern in Spring: https://spring.io/blog/2020/06/30/url-matching-with-pathpattern-in-spring-mvc
    //  - Test case: https://github.com/spring-projects/spring-framework/blob/main/spring-web/src/test/java/org/springframework/web/util/pattern/PathPatternTests.java
    registry.addResourceHandler("/app01/asset-manifest*") // for asset-manifest.json
        .addResourceLocations("classpath:/META-INF/resources/app-01/")
        .setCachePeriod(0);

    registry.addResourceHandler("/app01/**")
        .addResourceLocations("classpath:/META-INF/resources/app-01/")
        .setCachePeriod(STATIC_RESOURCE_CACHE_IN_SECONDS);

    // ---------------------------------------------------------------------------------
    // APP 02
    registry.addResourceHandler("/app02/**")
        .addResourceLocations("classpath:/META-INF/resources/app-02/")
        .setCachePeriod(STATIC_RESOURCE_CACHE_IN_SECONDS);
  }
}
