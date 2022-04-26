package com.tr.candlestickprovider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class RedisConfig {

    /**
     * Instantiate RedisConnectionFactory
     * @return instance of RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }


    /**
     * Instantiate Redis connection
     * @return istance of connected RedisTemplate
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate redis = new RedisTemplate();
        redis.setConnectionFactory(connectionFactory);
        return redis;
    }
}
