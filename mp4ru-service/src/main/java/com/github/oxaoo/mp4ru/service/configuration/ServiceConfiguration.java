package com.github.oxaoo.mp4ru.service.configuration;

import com.github.oxaoo.mp4ru.exceptions.InitRussianParserException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Value("${classifierModel.path}")
    private String classifierModelPath;

    @Value("${treeTagger.path}")
    private String treeTaggerPath;

    @Value("${parserConfig.path}")
    private String parserConfigPath;

    @Bean
    public RussianParser russianParserProvider() throws InitRussianParserException {
        return new RussianParser(this.classifierModelPath, this.treeTaggerPath, this.parserConfigPath);
    }
}
