package com.rig.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.security.cors")
public class CorsProperties {

    private String allowedOrigins;
    private String allowedMethods;
    private String maxAge;
    private String allowedHeaders;
    private String exposedHeaders;
}
