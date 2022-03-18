package org.tnmk.practicejson.pro02resourcepathregistry.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private static final int STATIC_RESOURCE_CACHE_IN_SECONDS = 3600; //3600 = 1 hour. 31536000 = 1 year

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/app01/**")
        .addResourceLocations("classpath:/META-INF/resources/app01/")
        .setCachePeriod(STATIC_RESOURCE_CACHE_IN_SECONDS);

    registry.addResourceHandler("/app02/**")
        .addResourceLocations("classpath:/META-INF/resources/app02/")
        .setCachePeriod(STATIC_RESOURCE_CACHE_IN_SECONDS);
  }

  @Bean
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }
}
