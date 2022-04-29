package com.tr.candlestickbuilder.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickbuilder.config.RabbitConfig;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentEventDTO;;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.service.InstrumentService;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
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

    public InstrumentListenerService(ObjectMapper objectMapper,
                                     InstrumentService instrumentService) {
        this.objectMapper = objectMapper;
        this.instrumentService = instrumentService;
    }

    /**
     * Gets the payload from instrument_queue
     * converts the payload to InstrumentEventDTO
     * saves the Instrument
     * @param payload
     */
    @RabbitListener(queues = {RabbitConfig.INSTRUMENT_QUEUE})
    public void instrumentListener(String payload) throws InstrumentNotFoundException {
        try {
            InstrumentEventDTO instrumentEventDTO = objectMapper.readValue(payload, InstrumentEventDTO.class);
            if (!(instrumentEventDTO.getInstrumentDTO() == null || instrumentEventDTO.getType() == null)) {
                logger.info("InstrumentEvent: ===> {}", instrumentEventDTO);
                saveInstrument(instrumentEventDTO);
            } else {
                logger.info("InstrumentEvent was empty!");
            }
        } catch (InstrumentNotFoundException e) {
            throw e;
        } catch (JsonProcessingException e) {
            logger.info("InstrumentEvent failed to convert: {}", e.getMessage());
        }

    }

    /**
     * Saves the instrument or updates the description and type
     * based on whether the object is in cache
     * @param instrumentEventDTO
     */
    public void saveInstrument(InstrumentEventDTO instrumentEventDTO) throws InstrumentNotFoundException {
        InstrumentDTO instrumentDTO = instrumentEventDTO.getInstrumentDTO();
        Type type = instrumentEventDTO.getType();
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
