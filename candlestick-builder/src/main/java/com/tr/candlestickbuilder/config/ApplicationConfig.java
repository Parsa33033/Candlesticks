package com.tr.candlestickbuilder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
