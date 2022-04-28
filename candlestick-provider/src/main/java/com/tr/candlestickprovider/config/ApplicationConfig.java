package com.tr.candlestickprovider.config;

import com.tr.candlestickprovider.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfig {

    @Bean
    public LoggingAspect getLoggingAspect() {
        return new LoggingAspect();
    }
}
