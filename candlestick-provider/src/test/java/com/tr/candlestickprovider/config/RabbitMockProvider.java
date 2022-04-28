package com.tr.candlestickprovider.config;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.github.fridujo.rabbitmq.mock.compatibility.MockConnectionFactoryFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableRabbit
@Configuration
public class RabbitMockProvider {

    @Bean
    AmqpAdmin admin() {
        AmqpAdmin admin = new RabbitAdmin(rabbit());
        admin.declareExchange(getTopicExchage());
        return admin;
    }

    @Bean
    public RabbitTemplate rabbit() {
        RabbitTemplate rabbitTemplate =  new RabbitTemplate();
        rabbitTemplate.setConnectionFactory((ConnectionFactory) connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(MockConnectionFactoryFactory.build());
    }

    @Bean
    public TopicExchange getTopicExchage() {
        return ExchangeBuilder.topicExchange(RabbitConfig.MAIN_EXCHANGE).build();
    }

}
