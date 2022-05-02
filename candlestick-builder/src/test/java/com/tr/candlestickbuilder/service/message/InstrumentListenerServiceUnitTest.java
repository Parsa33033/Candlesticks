package com.tr.candlestickbuilder.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.candlestickbuilder.model.InstrumentTest;
import com.tr.candlestickbuilder.model.InstrumentTestList;
import com.tr.candlestickbuilder.model.dto.InstrumentEventDTO;
import com.tr.candlestickbuilder.model.enums.ResultType;
import com.tr.candlestickbuilder.service.InstrumentService;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickbuilder.service.exceptions.InstrumentReceiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
    ResourceLoader.class,
    InstrumentService.class
})
class InstrumentListenerServiceUnitTest {
    Logger logger = LoggerFactory.getLogger(InstrumentListenerServiceUnitTest.class);
    String isin = "isin";

    @InjectMocks
    InstrumentListenerService instrumentListenerService;

    @Autowired
    ResourceLoader resourceLoader;

    InstrumentTestList stream;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        InstrumentService instrumentServiceMock = Mockito.mock(InstrumentService.class);
        instrumentListenerService = new InstrumentListenerService(objectMapper, instrumentServiceMock);

        Resource instruments = resourceLoader.getResource("classpath:instruments.json");
        URL url = instruments.getURL();
        stream = objectMapper.readValue(url, InstrumentTestList.class);
    }

    @Test
    public void testIfNewInstrumentWillBeCreatedIfNotInDataBase() throws InstrumentNotFoundException, JsonProcessingException {
        for (InstrumentTest test: stream.getInstrumentList()) {
            InstrumentEventDTO instrumentEventDTO = test.getInstrumentEventDTO();
            ResultType resultType = test.getResult();
            String payload = objectMapper.writeValueAsString(instrumentEventDTO);
            if (resultType == ResultType.INSTRUMENT_EXCEPTION) {
                assertThrows(InstrumentReceiveException.class, () -> instrumentListenerService.instrumentListener(payload));
            } else {
                assertDoesNotThrow(() -> instrumentListenerService.instrumentListener(payload));
            }
        }
    }
}