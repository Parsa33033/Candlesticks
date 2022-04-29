package com.tr.candlestickbuilder.service.impl;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.mapper.CandlestickHashMapper;
import com.tr.candlestickbuilder.model.mapper.InstrumentHashMapper;
import com.tr.candlestickbuilder.model.redis.InstrumentHash;
import com.tr.candlestickbuilder.repository.InstrumentHashRepository;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import redis.embedded.RedisServer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataRedisTest
class InstrumentHashServiceImplIntegrationTest {
    Logger logger = LoggerFactory.getLogger(InstrumentHashServiceImplIntegrationTest.class);
    static String isin = "isin";

    InstrumentHash instrumentHash;

    InstrumentHashServiceImpl instrumentHashService;

    InstrumentHashMapper instrumentHashMapper = Mappers.getMapper(InstrumentHashMapper.class);

    CandlestickHashMapper candlestickHashMapper = Mappers.getMapper(CandlestickHashMapper.class);

    @Autowired
    InstrumentHashRepository instrumentHashRepository;

    RedisServer redisServer;

    @BeforeEach
    public void setUp() {
        redisServer = RedisServer.builder().port(6379).build();
        redisServer.start();
        instrumentHashService = new InstrumentHashServiceImpl(
                instrumentHashRepository,
                instrumentHashMapper,
                candlestickHashMapper);
        instrumentHash = new InstrumentHash();
        instrumentHash.setIsin(isin);
    }

    @Test
    void assertThatInstrumentHashServiceImplWorksCorrectly() throws InstrumentNotFoundException {
        // check if empty
        boolean result = instrumentHashService.hasInstrument(isin);
        assertEquals(result, false);
        // add instance
        InstrumentDTO instrumentDTOSave = instrumentHashService.save(instrumentHashMapper.toDto(instrumentHash));
        // get the instance
        InstrumentDTO instrumentDTOGet = instrumentHashService.getByIsin(isin, 0);
        // check if save returned instance and get instance have the same string and contain the isin
        instrumentDTOSave.setCandlesticks(new ArrayList<>());
        assertEquals(instrumentDTOGet.toString(), instrumentDTOSave.toString());
        assertEquals(instrumentDTOGet.getIsin(), isin);
        //delete by isin and check if fetch returns an InstrumentNotFoundException
        instrumentHashService.deleteByIsin(isin);
        assertThrows(InstrumentNotFoundException.class,
                () -> instrumentHashService.getByIsin(isin, 0));
        // check if getting the ADDED instruments will be distinguished
        int count = 5;
        int n = 3;
        for (int i = 1; i <= count; i++) {
            String isinTmp = isin + i;
            instrumentDTOSave.setIsin(isinTmp);
            if (i <= n) instrumentDTOSave.setType(Type.ADD);
            else instrumentDTOSave.setType(Type.DELETE);
            instrumentHashService.save(instrumentDTOSave);
        }
        List<InstrumentDTO> x = instrumentHashService.getAllByType(Type.ADD);
        assertEquals(instrumentHashService.getAllByType(Type.ADD).size(), n);
        assertEquals(instrumentHashService.getAllByType(Type.DELETE).size(), count - n);
        assertEquals(instrumentHashService.getAll().size(), count);

        // check for delete all
        instrumentHashService.deleteAll();
        assertEquals(instrumentHashService.getAll().size(), 0);
    }

    @Test
    public void assertThatListOfCandlesticksWillHaveTheRightSizeAsNeeded() throws InstrumentNotFoundException {
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setIsin(isin);
        instrumentDTO.setCandlesticks(new ArrayList<>());
        instrumentHashService.save(instrumentDTO);
        int count = 40;
        for (int i = 1; i <= count; i++) {
            CandlestickDTO candlestickDTO = new CandlestickDTO();
            candlestickDTO.setIsin(isin);
            candlestickDTO.setOpenTimestamp(Instant.now().toString());
            instrumentDTO = instrumentHashService.getByIsin(isin, 0);
            instrumentDTO.getCandlesticks().add(candlestickDTO);
            instrumentHashService.save(instrumentDTO);
        }
        int limit = 30;
        assertEquals(instrumentHashService.getByIsin(isin, 0).getCandlesticks().size(),
                count);
        assertEquals(instrumentHashService.getByIsin(isin, limit).getCandlesticks().size(),
                limit);
        int n = 15;
        assertThat(n).isGreaterThan(count - limit);
        instrumentDTO = instrumentHashService.getByIsin(isin, 0);
        for (int i = 1; i <= n; i++) {
            instrumentDTO.getCandlesticks().remove(0);
        }
        instrumentHashService.save(instrumentDTO);
        assertEquals(instrumentHashService.getByIsin(isin, limit).getCandlesticks().size(),
                count - n);

    }

    @AfterEach
    public void closeUp() {
        redisServer.stop();;
    }

}