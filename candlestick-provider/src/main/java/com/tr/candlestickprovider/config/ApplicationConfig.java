package com.tr.candlestickprovider.config;

import com.tr.candlestickprovider.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfig {

    @Bean
    public LoggingAspect getLoggingAspect() {
        return new LoggingAspect();
    }
}
