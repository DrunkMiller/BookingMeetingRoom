package com.booking.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swagger")
@PropertySource("classpath:/swagger.properties")
@Data
public class SwaggerInfo {
    private String basePackage;
    private String antPath;
    private String title = " HTTP API";
    private String description;
    private String version;
    private Boolean enable;
    private String contactName;
    private String contactEmail;
    private String contactUrl;
    private String license;
    private String licenseUrl;


}
