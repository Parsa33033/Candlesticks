package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.dto.CandlestickDTO;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.enums.Type;
import com.tr.candlestickprovider.model.mongodb.InstrumentDocument;
import com.tr.candlestickprovider.model.redis.CandlestickHash;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

class InstrumentDocumentMapperUnitTest {
    String isin = "isin";

    Type type = Type.ADD;

    String description = "description";

    InstrumentDocument instrumentDocument;

    InstrumentDocumentMapper instrumentDocumentMapper = Mappers.getMapper(InstrumentDocumentMapper.class);

    @BeforeEach
    public void setUp() {
        instrumentDocument = new InstrumentDocument();
        instrumentDocument.setIsin(isin);
        instrumentDocument.setType(type);
        instrumentDocument.setDescription(description);
    }

    @Test
    public void checkIfInstrumentMapperConvertsToDTOAndBack() {
        InstrumentDTO dto = instrumentDocumentMapper.toDto(instrumentDocument);
        InstrumentDocument c = instrumentDocumentMapper.toEntity(dto);
        assertThat(instrumentDocument.toString()).isEqualTo(c.toString());
    }
}