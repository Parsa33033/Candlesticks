package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.redis.CandlestickHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class CandlestickHashMapperUnitTest {

    String isin = "isin";

    double openPrice = 1.0;

    double highPrice = 1.0;

    double lowPrice = 1.0;

    double closingPrice = 1.0;

    CandlestickHash candlestickHash;

    CandlestickHashMapper candlestickHashMapper = Mappers.getMapper(CandlestickHashMapper.class);


    @BeforeEach
    public void setUp() {
        Instant instant = Instant.now();
        candlestickHash = new CandlestickHash();
        candlestickHash.setIsin(isin);
        candlestickHash.setCloseTimestamp(instant);
        candlestickHash.setClosingPrice(closingPrice);
        candlestickHash.setHighPrice(highPrice);
        candlestickHash.setLowPrice(lowPrice);
        candlestickHash.setOpenPrice(openPrice);
        candlestickHash.setOpenTimestamp(instant);
    }

    @Test
    public void checkIfCandlestickMapperConvertsToDTOAndBack() {
        CandlestickDTO dto = candlestickHashMapper.toDto(candlestickHash);
        CandlestickHash c = candlestickHashMapper.toEntity(dto);
        assertThat(candlestickHash.toString()).isEqualTo(c.toString());
    }

    @Test
    public void checkIfCandlestickMapMapperConvertsToDTOAndBack() {
        CandlestickDTO candlestickDTO = new CandlestickDTO();
        candlestickDTO.setIsin(isin);
        Map<String, CandlestickDTO> m = new HashMap<>();
        m.put(isin, candlestickDTO);
        Map<String, CandlestickHash> m2 = candlestickHashMapper.toEntityMaps(m);
        Map<String, CandlestickDTO> m3 = candlestickHashMapper.toDtoMaps(m2);
        assertThat(m.toString()).isEqualTo(m3.toString());
    }
}