package com.tr.candlestickbuilder.model.mapper;

import com.tr.candlestickbuilder.model.dto.InstrumentDTO;
import com.tr.candlestickbuilder.model.redis.InstrumentHash;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface InstrumentHashMapper extends EntityMapper<InstrumentDTO, InstrumentHash> {
}
