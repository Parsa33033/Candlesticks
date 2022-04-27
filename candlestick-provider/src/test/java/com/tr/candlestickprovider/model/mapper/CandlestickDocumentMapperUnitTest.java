package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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