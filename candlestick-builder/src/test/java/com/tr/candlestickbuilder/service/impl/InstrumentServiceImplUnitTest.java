package com.tr.candlestickbuilder.service.impl;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import com.tr.candlestickbuilder.service.exceptions.InstrumentTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class InstrumentServiceImplUnitTest {

    InstrumentDocumentServiceImpl instrumentDocumentService;

    InstrumentHashServiceImpl instrumentHashService;

    @InjectMocks
    InstrumentServiceImpl instrumentService;

    @BeforeEach
    public void setUp() {
        instrumentHashService =
                Mockito.mock(InstrumentHashServiceImpl.class);
        instrumentDocumentService =
                Mockito.mock(InstrumentDocumentServiceImpl.class);

        instrumentService = new InstrumentServiceImpl(instrumentHashService, instrumentDocumentService);
    }

    @Test
    public void testIfRedisHasDataItReturnsFromRedisAndIfNotFromMongo() throws InstrumentNotFoundException {

        String isin = "isin";
        String redis = "redis";
        String mongo = "mongo";
        // redis
        InstrumentDTO instrumentDTORedis = new InstrumentDTO();
        instrumentDTORedis.setIsin(isin);
        instrumentDTORedis.setDescription(redis);

        // mongo
        InstrumentDTO instrumentDTOMongo = new InstrumentDTO();
        instrumentDTOMongo.setIsin(isin);
        instrumentDTOMongo.setDescription(mongo);

        // claim redis has hash
        Mockito.when(instrumentService.hasInstrument(isin)).thenReturn(true);
        Mockito.when(instrumentHashService.getByIsin(isin, 0)).thenReturn(instrumentDTORedis);
        Mockito.when(instrumentDocumentService.getByIsin(isin, 0)).thenReturn(instrumentDTOMongo);
        InstrumentDTO tmp = instrumentService.getByIsin(isin, 0);
        assertThat(tmp.toString()).isEqualTo(instrumentDTORedis.toString());

        // claim mongo has hash
        Mockito.when(instrumentService.hasInstrument(isin)).thenReturn(false);
        tmp = instrumentService.getByIsin(isin, 0);
        assertThat(tmp.toString()).isEqualTo(instrumentDTOMongo.toString());

    }

    @Test
    void testIfRedisHasAllDataItReturnsFromRedisAndIfNotFromMongoAndSavesInRedisAfter() throws InstrumentTypeException {

        String isin = "isin";
        String redis = "redis";
        String mongo = "mongo";
        Type type = Type.ADD;

        // redis
        InstrumentDTO instrumentDTORedis = new InstrumentDTO();
        instrumentDTORedis.setIsin(isin);
        instrumentDTORedis.setDescription(redis);
        List<InstrumentDTO> instrumentDTOSRedis = Arrays.asList(instrumentDTORedis);

        // mongo
        InstrumentDTO instrumentDTOMongo = new InstrumentDTO();
        instrumentDTOMongo.setIsin(isin);
        instrumentDTOMongo.setDescription(mongo);
        List<InstrumentDTO> instrumentDTOSMongo = Arrays.asList(instrumentDTOMongo);

        assertThrows(InstrumentTypeException.class, () -> instrumentService.getAllByType(null));

        // from redis
        Mockito.when(instrumentHashService.getAllByType(type)).thenReturn(instrumentDTOSRedis);
        Mockito.when(instrumentDocumentService.getAllByType(type)).thenReturn(null);

        assertThat(instrumentService.getAllByType(type).toString()).isEqualTo(instrumentDTOSRedis.toString());

        // from mongo
        Mockito.when(instrumentHashService.getAllByType(type)).thenReturn(null);
        Mockito.when(instrumentDocumentService.getAllByType(type)).thenReturn(instrumentDTOSMongo);

        assertThat(instrumentService.getAllByType(type).toString()).isEqualTo(instrumentDTOSMongo.toString());

        Mockito.when(instrumentHashService.getAllByType(type)).thenReturn(new ArrayList<>());
        assertThat(instrumentService.getAllByType(type).toString()).isEqualTo(instrumentDTOSMongo.toString());
    }

}