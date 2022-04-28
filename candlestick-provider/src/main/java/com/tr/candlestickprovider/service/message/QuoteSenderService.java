package com.tr.candlestickprovider.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.RabbitConfig;
import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.consts.RabbitRouteBuilder;
import com.tr.candlestickprovider.model.dto.*;
import com.tr.candlestickprovider.service.exceptions.InstrumentEvenNotSupportedException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventSendToQueueException;
import com.tr.candlestickprovider.service.exceptions.QuoteEventNotSupportedException;
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

    public QuoteSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
            if (isNotCorrect(quoteDTO)) throw new QuoteEventNotSupportedException(quoteEventDTO.toString());
            String route = RabbitRouteBuilder.from(Constant.QUOTE).toAnywhere().build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                    route,
                    quoteDTO);
        } catch (QuoteEventNotSupportedException e) {
            throw e;
        } catch (Exception e) {
            throw new PartnerEventSendToQueueException(quoteEventDTO.toString(), Constant.QUOTE, e.getMessage());
        }
    }

    private boolean isNotCorrect(QuoteDTO quoteDTO) {
        String isin = quoteDTO.getIsin();
        double price = quoteDTO.getPrice();
        String timestamp = quoteDTO.getTimestamp();
        return isin == null || isin.isEmpty() ||
                price < 0 || timestamp == null || timestamp.isEmpty();
    }

}
