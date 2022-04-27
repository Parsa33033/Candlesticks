package com.tr.candlestickprovider.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.RabbitConfig;
import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.consts.RabbitRouteBuilder;
import com.tr.candlestickprovider.model.dto.*;
import com.tr.candlestickprovider.service.impl.InstrumentHashServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

/**
 * Sends Quote messages to messaging queue
 */
@Service
public class QuoteSenderService {
    Logger logger = LoggerFactory.getLogger(QuoteSenderService.class);
    private final RabbitTemplate rabbitTemplate;

    private final InstrumentHashServiceImpl instrumentService;

    private final ObjectMapper objectMapper;

    private final TopicExchange topicExchange;

    private final static Random rand = new Random();

    public QuoteSenderService(RabbitTemplate rabbitTemplate,
                              InstrumentHashServiceImpl instrumentService,
                              ObjectMapper objectMapper,
                              @Qualifier("quoteExchange") TopicExchange topicExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.instrumentService = instrumentService;
        this.objectMapper = objectMapper;
        this.topicExchange = topicExchange;
    }

    /**
     * Save the incoming Quote isin in memory and send the the affiliated object to the associated
     * messaging queue
     * @param quoteEventDTO
     * @throws JsonProcessingException
     */
    public void send(QuoteEventDTO quoteEventDTO) {
        try {
            QuoteDTO quoteDTO = quoteEventDTO.getQuoteDTO();
            quoteDTO.setTimestamp(Instant.now().toString());
            String route = RabbitRouteBuilder.from(Constant.QUOTE).toAnywhere().build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                    route,
                    quoteDTO);
        } catch (Exception e) {
            logger.error("Quote Error: {}", e.getMessage());
        }
    }

}
