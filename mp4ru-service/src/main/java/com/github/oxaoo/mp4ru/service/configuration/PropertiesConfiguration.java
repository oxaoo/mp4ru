package com.github.oxaoo.mp4ru.service.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mp4ru")
public class PropertiesConfiguration {
    private String classifierModelPath;
    private String treeTaggerPath;
    private String parserConfigurationPath;
}
