package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.InstrumentDocument;
import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.redis.Instrument;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface InstrumentDocumentMapper extends EntityMapper<InstrumentDTO, InstrumentDocument> {
}
