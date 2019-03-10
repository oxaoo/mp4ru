package com.github.oxaoo.mp4ru.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.oxaoo.mp4ru.exceptions.InitRussianParserException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    private PropertiesConfiguration properties;

    @Bean
    public RussianParser russianParserProvider() throws InitRussianParserException {
        return new RussianParser(
                this.properties.getClassifierModelPath(),
                this.properties.getTreeTaggerPath(),
                this.properties.getParserConfigurationPath());
    }

    @Bean
    public ObjectMapper objectMapperProvider() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PropertyNamingStrategyBase() {
            @Override
            public String translate(final String value) {
                return value != null ? value.toUpperCase() : null;
            }
        });
        return objectMapper;
    }

    @Autowired
    public void setProperties(final PropertiesConfiguration properties) {
        this.properties = properties;
    }
}
