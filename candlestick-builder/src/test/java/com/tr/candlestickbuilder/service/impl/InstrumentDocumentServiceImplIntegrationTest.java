package com.tr.candlestickbuilder.service.impl;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.mapper.CandlestickDocumentMapper;
import com.tr.candlestickbuilder.model.mapper.InstrumentDocumentMapper;
import com.tr.candlestickbuilder.model.mongodb.InstrumentDocument;
import com.tr.candlestickbuilder.repository.InstrumentDocumentRepository;
import com.tr.candlestickbuilder.service.exceptions.InstrumentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class InstrumentDocumentServiceImplIntegrationTest {
    Logger logger = LoggerFactory.getLogger(InstrumentDocumentServiceImplIntegrationTest.class);
    static String isin = "isin";

    InstrumentDocument instrumentDocument;

    InstrumentDocumentServiceImpl instrumentDocumentService;

    InstrumentDocumentMapper instrumentDocumentMapper = Mappers.getMapper(InstrumentDocumentMapper.class);

    CandlestickDocumentMapper candlestickDocumentMapper = Mappers.getMapper(CandlestickDocumentMapper.class);

    @Autowired
    InstrumentDocumentRepository instrumentDocumentRepository;


    @BeforeEach
    public void setUp() {
        instrumentDocumentService = new InstrumentDocumentServiceImpl(
                instrumentDocumentRepository,
                instrumentDocumentMapper,
                candlestickDocumentMapper);
        instrumentDocumentService.deleteAll();
        instrumentDocument = new InstrumentDocument();
        instrumentDocument.setIsin(isin);
    }

    @Test
    void assertThatInstrumentDocumentServiceImplWorksCorrectly() throws InstrumentNotFoundException {
        // check if empty
        boolean result = instrumentDocumentService.hasInstrument(isin);
        assertEquals(result, false);
        // add instance
        InstrumentDTO instrumentDTOSave = instrumentDocumentService.save(instrumentDocumentMapper.toDto(instrumentDocument));
        // get the instance
        InstrumentDTO instrumentDTOGet = instrumentDocumentService.getByIsin(isin, 0);
        // check if save returned instance and get instance have the same string and contain the isin
        instrumentDTOSave.setCandlesticks(new HashMap<>());
        assertEquals(instrumentDTOGet.toString(), instrumentDTOSave.toString());
        assertEquals(instrumentDTOGet.getIsin(), isin);
        //delete by isin and check if fetch returns an InstrumentNotFoundException
        instrumentDocumentService.deleteByIsin(isin);
        assertThrows(InstrumentNotFoundException.class,
                () -> instrumentDocumentService.getByIsin(isin, 0));
        // check if getting the ADDED instruments will be distinguished
        int count = 5;
        int n = 3;
        for (int i = 1; i <= count; i++) {
            String isinTmp = isin + i;
            instrumentDTOSave.setIsin(isinTmp);
            if (i <= n) instrumentDTOSave.setType(Type.ADD);
            else instrumentDTOSave.setType(Type.DELETE);
            instrumentDocumentService.save(instrumentDTOSave);
        }
        List<InstrumentDTO> x = instrumentDocumentService.getAllByType(Type.ADD);
        assertEquals(instrumentDocumentService.getAllByType(Type.ADD).size(), n);
        assertEquals(instrumentDocumentService.getAllByType(Type.DELETE).size(), count - n);
        assertEquals(instrumentDocumentService.getAll().size(), count);

        // check for delete all
        instrumentDocumentService.deleteAll();
        assertEquals(instrumentDocumentService.getAll().size(), 0);
    }

    @Test
    public void assertThatListOfCandlesticksWillHaveTheRightSizeAsNeeded() throws InstrumentNotFoundException {
        InstrumentDTO instrumentDTO = new InstrumentDTO();
        instrumentDTO.setIsin(isin);
        instrumentDTO.setCandlesticks(new HashMap<>());
        instrumentDocumentService.save(instrumentDTO);
        int count = 40;
        for (int i = 1; i <= count; i++) {
            CandlestickDTO candlestickDTO = new CandlestickDTO();
            candlestickDTO.setIsin(isin);
            candlestickDTO.setOpenTimestamp(Instant.now().toString());
            instrumentDTO = instrumentDocumentService.getByIsin(isin, 0);
            instrumentDTO.getCandlesticks().put(i + "", candlestickDTO);
            instrumentDocumentService.save(instrumentDTO);
        }
        int limit = 30;
        assertEquals(instrumentDocumentService.getByIsin(isin, 0).getCandlesticks().size(),
                count);
        assertEquals(instrumentDocumentService.getByIsin(isin, limit).getCandlesticks().size(),
                limit);
        int n = 5;
        assertThat(n).isLessThan(count - limit);
        instrumentDTO = instrumentDocumentService.getByIsin(isin, 0);
        for (int i = 1; i <= n; i++) {
            instrumentDTO.getCandlesticks().remove(0);
        }
        instrumentDocumentService.save(instrumentDTO);
        assertEquals(instrumentDocumentService.getByIsin(isin, limit).getCandlesticks().size(),
                limit);

    }

}