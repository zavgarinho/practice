package com.zavga.news.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
public class ConfigProperties {
    private String pageTitle;
}
