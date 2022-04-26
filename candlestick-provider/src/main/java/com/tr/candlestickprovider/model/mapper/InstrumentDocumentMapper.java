package com.tr.candlestickprovider.model.mapper;

import com.tr.candlestickprovider.model.InstrumentDocument;
import com.tr.candlestickprovider.model.dto.InstrumentDTO;
import com.tr.candlestickprovider.model.redis.Instrument;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface InstrumentDocumentMapper extends EntityMapper<InstrumentDTO, InstrumentDocument> {
}
