package com.tr.candlestickbuilder.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickbuilder.config.RabbitConfig;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentEventDTO;;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.service.InstrumentDocumentService;
import com.tr.candlestickbuilder.service.InstrumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InstrumentListenerService {
    private final static int CANDLESTICK_STACK_LIMIT = 30;
    Logger logger = LoggerFactory.getLogger(InstrumentListenerService.class);

    private final ObjectMapper objectMapper;

    private final InstrumentService instrumentService;

    private final InstrumentDocumentService instrumentDocumentService;

    public InstrumentListenerService(ObjectMapper objectMapper,
                                     InstrumentService instrumentService,
                                     InstrumentDocumentService instrumentDocumentService) {
        this.objectMapper = objectMapper;
        this.instrumentService = instrumentService;
        this.instrumentDocumentService = instrumentDocumentService;
    }

    @RabbitListener(queues = {RabbitConfig.INSTRUMENT_QUEUE})
    public void instrumentListener(String payload) {
        try {
            InstrumentEventDTO instrumentEventDTO = objectMapper.readValue(payload, InstrumentEventDTO.class);
            if (!(instrumentEventDTO.getInstrumentDTO() == null || instrumentEventDTO.getType() == null)) {
                logger.info("InstrumentEvent: ===> {}", instrumentEventDTO);
                saveInstrument(instrumentEventDTO);
            } else {
                logger.info("InstrumentEvent was empty!");
            }
        } catch (JsonProcessingException e) {
            logger.info("InstrumentEvent failed to convert: {}", e.getMessage());
        }

    }

    public void saveInstrument(InstrumentEventDTO instrumentEventDTO) {
        Type type = instrumentEventDTO.getType();
        InstrumentDTO instrumentDTO = instrumentEventDTO.getInstrumentDTO();
        instrumentDTO.setType(type);
        String isin = instrumentDTO.getIsin();
        if (instrumentService.hasInstrument(isin)) {
            InstrumentDTO previous = instrumentService.getByIsin(isin, CANDLESTICK_STACK_LIMIT);
            previous.setDescription(instrumentDTO.getDescription());
            previous.setType(type);
            instrumentService.save(previous);
        } else {
            instrumentDTO.setCandlesticks(new ArrayList<>());
            instrumentService.save(instrumentDTO);
        }
    }
}
