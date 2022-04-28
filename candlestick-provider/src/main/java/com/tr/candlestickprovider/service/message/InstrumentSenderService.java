package com.tr.candlestickprovider.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickprovider.config.RabbitConfig;
import com.tr.candlestickprovider.consts.Constant;
import com.tr.candlestickprovider.consts.RabbitRouteBuilder;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.dto.InstrumentEventDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.service.exceptions.InstrumentEvenNotSupportedException;
import com.tr.candlestickprovider.service.exceptions.PartnerEventSendToQueueException;
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
 * Sends Instrument messages to messaging queue
 */
@Service
public class InstrumentSenderService {
    Logger logger = LoggerFactory.getLogger(InstrumentSenderService.class);

    private final RabbitTemplate rabbitTemplate;


    public InstrumentSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Save the incoming instrument isin in memory and send the the affiliated object to the
     * messaging queue
     *
     * @param instrumentEventDTO An instance of IntrumentEventDTO
     * @throws JsonProcessingException
     */
    public void send(InstrumentEventDTO instrumentEventDTO) {
        try {
            instrumentEventDTO.getInstrumentDTO().setTimestamp(Instant.now().toString());
            if (isNotCorrect(instrumentEventDTO))
                throw new InstrumentEvenNotSupportedException(instrumentEventDTO.toString());
            String route = RabbitRouteBuilder.from(Constant.INSTRUMENT).toAnywhere().build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.MAIN_EXCHANGE,
                    route,
                    instrumentEventDTO);
        } catch (InstrumentEvenNotSupportedException e) {
            throw e;
        } catch (Exception e) {
            throw new PartnerEventSendToQueueException(instrumentEventDTO.toString(), Constant.INSTRUMENT, e.getMessage());
        }
    }

    public boolean isNotCorrect(InstrumentEventDTO instrumentEventDTO) {
        Type type = instrumentEventDTO.getType();
        InstrumentDTO instrumentDTO = instrumentEventDTO.getInstrumentDTO();
        String timestamp = instrumentDTO.getTimestamp();
        String description = instrumentDTO.getDescription();
        String isin = instrumentDTO.getIsin();
        return isin == null || isin.isEmpty() ||
                description == null || description.isEmpty() ||
                timestamp == null || timestamp.isEmpty() ||
                !(type == Type.ADD || type == Type.DELETE);
    }
}
