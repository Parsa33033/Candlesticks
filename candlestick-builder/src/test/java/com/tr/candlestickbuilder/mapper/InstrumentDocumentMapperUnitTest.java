package com.tr.candlestickbuilder.mapper;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.enums.Type;
import com.tr.candlestickbuilder.model.mapper.InstrumentDocumentMapper;
import com.tr.candlestickbuilder.model.mongodb.InstrumentDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

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