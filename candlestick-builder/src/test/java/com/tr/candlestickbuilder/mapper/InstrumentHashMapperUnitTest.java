package com.tr.candlestickbuilder.mapper;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.mapper.InstrumentHashMapper;
import com.tr.candlestickbuilder.model.redis.InstrumentHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;


class InstrumentHashMapperUnitTest {
    String isin = "isin";

    Type type = Type.ADD;

    String description = "description";

    InstrumentHash instrumentHash;

    InstrumentHashMapper instrumentHashMapper = Mappers.getMapper(InstrumentHashMapper.class);

    @BeforeEach
    public void setUp() {
        instrumentHash = new InstrumentHash();
        instrumentHash.setIsin(isin);
        instrumentHash.setType(type);
        instrumentHash.setDescription(description);
    }

    @Test
    public void checkIfInstrumentMapperConvertsToDTOAndBack() {
        InstrumentDTO dto = instrumentHashMapper.toDto(instrumentHash);
        InstrumentHash c = instrumentHashMapper.toEntity(dto);
        assertThat(instrumentHash.toString()).isEqualTo(c.toString());
    }

}