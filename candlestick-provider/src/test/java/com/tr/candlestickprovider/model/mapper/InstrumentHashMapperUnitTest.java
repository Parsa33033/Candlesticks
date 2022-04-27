package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.model.redis.InstrumentHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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