package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.mongodb.CandlestickDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void checkIfCandlestickMapMapperConvertsToDTOAndBack() {
        CandlestickDTO candlestickDTO = new CandlestickDTO();
        candlestickDTO.setIsin(isin);
        Map<String, CandlestickDTO> m = new HashMap<>();
        m.put(isin, candlestickDTO);
        Map<String, CandlestickDocument> m2 = candlestickDocumentMapper.toEntityMaps(m);
        Map<String, CandlestickDTO> m3 = candlestickDocumentMapper.toDtoMaps(m2);
        assertThat(m.toString()).isEqualTo(m3.toString());
    }
}