package com.github.oxaoo.mp4ru.service.configuration;

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

    @Autowired
    public void setProperties(final PropertiesConfiguration properties) {
        this.properties = properties;
    }
}
