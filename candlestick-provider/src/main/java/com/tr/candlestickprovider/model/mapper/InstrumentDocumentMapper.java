package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.mongodb.InstrumentDocument;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface InstrumentDocumentMapper extends EntityMapper<InstrumentDTO, InstrumentDocument> {
}
