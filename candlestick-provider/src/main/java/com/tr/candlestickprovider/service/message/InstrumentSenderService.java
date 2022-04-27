package com.tr.candlestickprovider.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.RabbitConfig;
import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.consts.RabbitRouteBuilder;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Sends Instrument messages to messaging queue
 */
@Service
public class InstrumentSenderService {
    Logger logger = LoggerFactory.getLogger(InstrumentSenderService.class);

    private final RabbitTemplate rabbitTemplate;

    private final InstrumentHashServiceImpl instrumentService;

    private final TopicExchange topicExchange;

    private final ObjectMapper objectMapper;

    private final static Random rand = new Random();


    public InstrumentSenderService(RabbitTemplate rabbitTemplate,
                                   InstrumentHashServiceImpl instrumentService,
                                   @Qualifier("quoteExchange") TopicExchange topicExchange,
                                   ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.instrumentService = instrumentService;
        this.topicExchange = topicExchange;
        this.objectMapper = objectMapper;
    }

    /**
     * Save the incoming instrument isin in memory and send the the affiliated object to the
     * messaging queue
     * @param instrumentEventDTO An instance of IntrumentEventDTO
     * @throws JsonProcessingException
     */
    public void send(InstrumentEventDTO instrumentEventDTO) throws JsonProcessingException {
        try {
            String route = RabbitRouteBuilder.from(Constant.INSTRUMENT).toAnywhere().build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                    route,
                    instrumentEventDTO);
        } catch (Exception e) {
            logger.error("Instrument Error: {}", e.getMessage());
        }
    }
}
