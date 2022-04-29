package com.tr.candlestickbuilder.mapper;

import com.tr.candlestickbuilder.model.dto.CandlestickDTO;
import com.tr.candlestickbuilder.model.mapper.CandlestickDocumentMapper;
import com.tr.candlestickbuilder.model.mongodb.CandlestickDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CandlestickDocumentMapperUnitTest {

    String isin = "isin";

    double openPrice = 1.0;

    double highPrice = 1.0;

    double lowPrice = 1.0;

    double closingPrice = 1.0;

    CandlestickDocument candlestickDocument;

    CandlestickDocumentMapper candlestickDocumentMapper = Mappers.getMapper(CandlestickDocumentMapper.class);


    @BeforeEach
    public void setUp() {
        Instant instant = Instant.now();
        candlestickDocument = new CandlestickDocument();
        candlestickDocument.setIsin(isin);
        candlestickDocument.setCloseTimestamp(instant);
        candlestickDocument.setClosingPrice(closingPrice);
        candlestickDocument.setCurrentTimestamp(instant);
        candlestickDocument.setHighPrice(highPrice);
        candlestickDocument.setLowPrice(lowPrice);
        candlestickDocument.setOpenPrice(openPrice);
        candlestickDocument.setOpenTimestamp(instant);
    }

    @Test
    public void checkIfCandlestickMapperConvertsToDTOAndBack() {
        CandlestickDTO dto = candlestickDocumentMapper.toDto(candlestickDocument);
        CandlestickDocument c = candlestickDocumentMapper.toEntity(dto);
        assertThat(candlestickDocument.toString()).isEqualTo(c.toString());
    }
}