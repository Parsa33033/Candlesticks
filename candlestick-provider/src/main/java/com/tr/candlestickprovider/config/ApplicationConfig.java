package com.tr.candlestickprovider.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.tr.candlestickprovider.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableAutoConfiguration
public class ApplicationConfig {

    @Bean
    public LoggingAspect getLoggingAspect() {
        return new LoggingAspect();
    }


}
