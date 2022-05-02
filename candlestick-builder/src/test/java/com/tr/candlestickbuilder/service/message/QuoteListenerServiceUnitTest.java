package com.tr.candlestickbuilder.service.message;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickbuilder.model.QuoteTest;
import com.tr.candlestickbuilder.model.QuoteTestList;
import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.dto.QuoteDTO;
import com.tr.candlestickbuilder.model.enums.ResultType;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.service.InstrumentService;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickbuilder.service.exceptions.QuoteReceiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class QuoteListenerServiceUnitTest {
    Logger logger = LoggerFactory.getLogger(QuoteListenerServiceUnitTest.class);

    @InjectMocks
    QuoteListenerService quoteListenerService;

    @Autowired
    ResourceLoader resourceLoader;

    QuoteTestList stream;

    InstrumentService instrumentServiceMock;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        instrumentServiceMock = Mockito.mock(InstrumentService.class);
        quoteListenerService = new QuoteListenerService(objectMapper, instrumentServiceMock);

        Resource resource = resourceLoader.getResource("classpath:quotes.json");
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        stream = objectMapper.readValue(resource.getURL(), QuoteTestList.class);
    }

    @Test
    void testIfQuoteListenerServiceWorksProperly() throws InstrumentNotFoundException {
        for (QuoteTest quoteTest : stream.getQuoteTestList()) {
            QuoteDTO quoteDTO = quoteTest.getQuoteDTO();
            String isin = quoteDTO.getIsin();
            ResultType type = quoteTest.getResultType();
            Mockito.when(instrumentServiceMock.hasInstrument(isin)).thenReturn(true);
            String timestamp = Instant.now().toString();
            Mockito.when(instrumentServiceMock.getByIsin(isin, 0))
                    .thenReturn(createNewInstrumentDTO(isin, timestamp, 0));
            if (type == ResultType.OK) {
                assertDoesNotThrow(() -> quoteListenerService.updateCandleStick(quoteDTO));
            } else if (type == ResultType.QUOTE_EXCEPTION) {
                assertThrows(QuoteReceiveException.class, () -> quoteListenerService.updateCandleStick(quoteDTO));
            }
        }
    }

    InstrumentDTO createNewInstrumentDTO(String isin,
                                         String timestamp,
                                         double price) {
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setType(Type.ADD);
        instrumentDTO.setIsin(isin);
        instrumentDTO.setTimestamp(timestamp);
        Map<String, CandlestickDTO> map = new HashMap<>();
        map.put(timestamp, createNewCandleStick(isin, timestamp, price));
        instrumentDTO.setCandlesticks(map);
        instrumentDTO.setDescription("description");
        return instrumentDTO;
    }

    CandlestickDTO createNewCandleStick(String isin,
                         String timestamp,
                         double price) {
        CandlestickDTO candlestickDTO = new CandlestickDTO();
        candlestickDTO.setOpenPrice(price);
        candlestickDTO.setIsin(isin);
        candlestickDTO.setClosingPrice(price);
        candlestickDTO.setHighPrice(price);
        candlestickDTO.setLowPrice(price);
        candlestickDTO.setOpenTimestamp(timestamp);
        candlestickDTO.setCloseTimestamp(timestamp);
        return candlestickDTO;
    }
}