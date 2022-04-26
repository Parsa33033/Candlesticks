package com.tr.candlestickbuilder.config;

import com.tr.candlestickbuilder.consts.Constant;
import com.tr.candlestickbuilder.consts.RabbitRouteBuilder;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

@Configuration
@EnableRabbit
public class RabbitConfig {
    public static final String MAIN_EXCHANGE = "main_exchange";
    public static final String INSTRUMENT_EXCHANGE = "instrument_exchange";
    public static final String QUOTE_EXCHANGE = "quote_exchange";
    public static final String INSTRUMENT_QUEUE = "instrument_queue";
    public static final String QUOTE_QUEUE = "quote_queue";

    @Bean("mainExchange")
    public TopicExchange mainExchange() {
        return ExchangeBuilder.topicExchange(MAIN_EXCHANGE).build();
    }

    @Bean("quoteExchange")
    public TopicExchange quoteExchange() {
        return ExchangeBuilder.topicExchange(QUOTE_EXCHANGE).build();
    }

    @Bean("instrumentExchange")
    public TopicExchange instrumentExchange() {
        return ExchangeBuilder.topicExchange(INSTRUMENT_EXCHANGE).build();
    }

    /**
     * Binding Queue exchange to Main exchange by topic key of quote.*.*
     * @return
     */
    @Bean
    public Binding bindQuoteToMain() {
        return BindingBuilder.bind(quoteExchange()).to(mainExchange())
                .with((RabbitRouteBuilder.from(Constant.QUOTE).toAnywhere().toAnywhere().build()));
    }

    /**
     * Binding Instrument exchange to Main exchange by topic key of instrument.*.*
     * @return
     */
    @Bean
    public Binding bindInstrumentToMain() {
        return BindingBuilder.bind(instrumentExchange()).to(mainExchange())
                .with((RabbitRouteBuilder.from(Constant.INSTRUMENT).toAnywhere().toAnywhere().build()));
    }

    @Bean
    public Queue quoteQueue() {
        return QueueBuilder.durable(QUOTE_QUEUE).build();
    }

    @Bean
    public Binding quoteBinding() {
        return BindingBuilder.bind(quoteQueue()).to(quoteExchange())
                .with(RabbitRouteBuilder.from(Constant.QUOTE).toAnywhere().build());
    }

    @Bean
    public Queue instruemntQueue() {
        return QueueBuilder.durable(INSTRUMENT_QUEUE).build();
    }

    @Bean
    public Binding instrumentBinding() {
        return BindingBuilder.bind(instruemntQueue()).to(instrumentExchange())
                .with(RabbitRouteBuilder.from(Constant.INSTRUMENT).toAnywhere().build());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }
}
